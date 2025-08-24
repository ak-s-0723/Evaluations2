package org.example.evaluations2.repos;

import org.example.evaluations2.models.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege,Long> {
    Privilege findByName(String name);
}
