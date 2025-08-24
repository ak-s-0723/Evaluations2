package org.example.evaluations2.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity(name="privileges_")
@Setter
@Getter
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
}
