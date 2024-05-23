package no.ntnu.project.group4.webapp.repositories;

import no.ntnu.project.group4.webapp.models.Provider;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The ProviderRepository class represents the repository class for the provider entity.
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
@Repository
public interface ProviderRepository extends CrudRepository<Provider, Long> {
}
