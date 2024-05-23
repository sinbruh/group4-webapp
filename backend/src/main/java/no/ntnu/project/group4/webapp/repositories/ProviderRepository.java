package no.ntnu.project.group4.webapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import no.ntnu.project.group4.webapp.models.Provider;

/**
 * The ProviderRepository class represents the repository class for the provider entity.
 * 
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
@Repository
public interface ProviderRepository extends CrudRepository<Provider, Long> {
}
