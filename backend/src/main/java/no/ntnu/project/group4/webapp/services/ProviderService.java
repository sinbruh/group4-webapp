package no.ntnu.project.group4.webapp.services;

import java.util.Optional;
import no.ntnu.project.group4.webapp.models.Provider;
import no.ntnu.project.group4.webapp.repositories.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The ProviderService class represents the service class for the provider entity.
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
@Service
public class ProviderService {
  @Autowired
  private ProviderRepository providerRepository;

  /**
   * Returns all providers in the database.
   *
   * @return All providers in the database
   */
  public Iterable<Provider> getAll() {
    return this.providerRepository.findAll();
  }
  
  /**
   * Returns the provider with the specified ID regardless of if it exists or not.
   *
   * @param id The specified ID
   * @return The provider with the specified ID regardless of if it exists or not
   */
  public Optional<Provider> getOne(Long id) {
    return this.providerRepository.findById(id);
  }

  /**
   * Returns the generated ID of the specified provider if it is added to the database.
   *
   * @param provider The specified provider
   * @return The generated ID of the specified provider if it is added to the database
   * @throws IllegalArgumentException If the specified provider is invalid
   */
  public Long add(Provider provider) {
    if (!provider.isValid()) {
      throw new IllegalArgumentException("Provider is invalid");
    }
    this.providerRepository.save(provider);
    return provider.getId();
  }

  /**
   * Returns true if the provider with the specified ID is found and updated with the specified
   * provider or false otherwise.
   *
   * @param id       The specified ID
   * @param provider The specified provider
   * @return True if the provider with the specified ID is found and updated with the specified
   *         provider or false otherwise
   * @throws IllegalArgumentException If the specified provider is invalid
   */
  public boolean update(Long id, Provider provider) {
    Optional<Provider> existingProvider = this.providerRepository.findById(id);
    if (!provider.isValid()) {
      throw new IllegalArgumentException("Provider is invalid");
    }
    if (existingProvider.isPresent()) {
      Provider existingProviderObj = existingProvider.get();
      existingProviderObj.setName(provider.getName());
      existingProviderObj.setPrice(provider.getPrice());
      existingProviderObj.setLocation(provider.getLocation());
      existingProviderObj.setAvailable(provider.isAvailable());
      existingProviderObj.setVisible(provider.isVisible());
      this.providerRepository.save(existingProviderObj);
    }
    return existingProvider.isPresent();
  }
  
  /**
   * Returns true if the provider with the specified ID is found and deleted or false otherwise.
   *
   * @param id The specified ID
   * @return True if the provider with the specified ID is found and deleted or false otherwise
   */
  public boolean delete(Long id) {
    Optional<Provider> provider = this.providerRepository.findById(id);
    if (provider.isPresent()) {
      this.providerRepository.deleteById(id);
    }
    return provider.isPresent();
  }
}
