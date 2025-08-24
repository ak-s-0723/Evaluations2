package org.example.evaluations2.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity(name="roles_")
@Setter
@Getter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @ManyToMany
    @JoinTable(
            name = "roles_privileges_",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))

    private Collection<Privilege> privileges;
}