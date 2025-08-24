package org.example.evaluations2.components;

import org.example.evaluations2.models.Privilege;
import org.example.evaluations2.models.Role;
import org.example.evaluations2.repos.PrivilegeRepository;
import org.example.evaluations2.repos.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.event.ContextRefreshedEvent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class SetupDataLoaderTests {

    private RoleRepository roleRepository;
    private PrivilegeRepository privilegeRepository;
    private SetupDataLoader setupDataLoader;

    @BeforeEach
    void setUp() {
        roleRepository = mock(RoleRepository.class);
        privilegeRepository = mock(PrivilegeRepository.class);
        setupDataLoader = new SetupDataLoader();
        setupDataLoader.roleRepository = roleRepository;
        setupDataLoader.privilegeRepository = privilegeRepository;
    }

    @Test
    void shouldCreatePrivilegesIfNotFound() {
        when(privilegeRepository.findByName("READ_PRIVILEGE")).thenReturn(null);
        when(privilegeRepository.findByName("WRITE_PRIVILEGE")).thenReturn(null);

        setupDataLoader.onApplicationEvent(mock(ContextRefreshedEvent.class));

        ArgumentCaptor<Privilege> captor = ArgumentCaptor.forClass(Privilege.class);
        verify(privilegeRepository, times(2)).save(captor.capture());

        assertThat(captor.getAllValues())
                .extracting(Privilege::getName)
                .containsExactlyInAnyOrder("READ_PRIVILEGE", "WRITE_PRIVILEGE");
    }

    @Test
    void shouldCreateRolesWithCorrectPrivileges() {
        Privilege read = new Privilege("READ_PRIVILEGE");
        Privilege write = new Privilege("WRITE_PRIVILEGE");

        when(privilegeRepository.findByName("READ_PRIVILEGE")).thenReturn(read);
        when(privilegeRepository.findByName("WRITE_PRIVILEGE")).thenReturn(write);
        when(roleRepository.findByName(anyString())).thenReturn(null);

        setupDataLoader.onApplicationEvent(mock(ContextRefreshedEvent.class));

        ArgumentCaptor<Role> captor = ArgumentCaptor.forClass(Role.class);
        verify(roleRepository, times(2)).save(captor.capture());

        Role adminRole = captor.getAllValues().stream()
                .filter(r -> r.getName().equals("ROLE_ADMIN")).findFirst().orElseThrow();
        Role userRole = captor.getAllValues().stream()
                .filter(r -> r.getName().equals("ROLE_USER")).findFirst().orElseThrow();

        assertThat(adminRole.getPrivileges())
                .extracting(Privilege::getName)
                .containsExactlyInAnyOrder("READ_PRIVILEGE", "WRITE_PRIVILEGE");

        assertThat(userRole.getPrivileges())
                .extracting(Privilege::getName)
                .containsExactly("READ_PRIVILEGE");
    }

    @Test
    void shouldNotRunAgainIfAlreadySetup() {
        setupDataLoader.alreadySetup = true;

        setupDataLoader.onApplicationEvent(mock(ContextRefreshedEvent.class));

        verifyNoInteractions(privilegeRepository);
        verifyNoInteractions(roleRepository);
    }
}
