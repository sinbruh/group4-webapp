package no.ntnu.project.group4.webapp.repositories;

import no.ntnu.project.group4.webapp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The RoleRepository class represents the repository class for the role entity.
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Role findOneByName(String name);
}
