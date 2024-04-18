package no.ntnu.project.group4.webapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import no.ntnu.project.group4.webapp.models.Provider;

@Repository
public interface ProviderRepository extends CrudRepository<Provider, Long> {
}
