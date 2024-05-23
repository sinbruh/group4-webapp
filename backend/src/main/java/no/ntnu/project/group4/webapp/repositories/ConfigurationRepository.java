package no.ntnu.project.group4.webapp.repositories;

import no.ntnu.project.group4.webapp.models.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The ConfigurationRepository class represents the repository class for the Configuration entity.
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
@Repository
public interface ConfigurationRepository extends CrudRepository<Configuration, Long> {
}
