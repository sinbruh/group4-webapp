package no.ntnu.project.group4.webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import no.ntnu.project.group4.webapp.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Role findOneByName(String name);
}
