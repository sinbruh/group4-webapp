package no.ntnu.project.group4.webapp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.ntnu.project.group4.webapp.models.Provider;
import no.ntnu.project.group4.webapp.repositories.ProviderRepository;

@Service
public class ProviderService {
  @Autowired
  private ProviderRepository providerRepository;

  /**
   * Gets all providers in the database.
   * 
   * @return All providers in the database
   */
  public Iterable<Provider> getAll() {
    return this.providerRepository.findAll();
  }
  
  /**
   * Gets the provider with the specified ID regardless of if it exists or not.
   * 
   * @param id The specified ID
   * @return The provider with the specified ID regardless of if it exists or not
   */
  public Optional<Provider> getOne(Long id) {
    return this.providerRepository.findById(id);
  }

  /**
   * Tries to add the specified provider to the database.
   * 
   * @param provider The specified provider
   * @return The ID of the specified provider added to the database
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
   * Tries to delete the provider with the specified ID.
   * 
   * @param id The specified ID
   * @return True if the provider with the specified ID was found and thus deleted or false
   *         otherwise
   */
  public boolean delete(Long id) {
    Optional<Provider> provider = this.providerRepository.findById(id);
    if (provider.isPresent()) {
      this.providerRepository.deleteById(id);
    }
    return provider.isPresent();
  }
  
  /**
   * Tries to update the provider with the specified ID to the specified provider. The ID of the
   * provider must match the specified ID.
   * 
   * @param id The specified ID
   * @param provider The specified provider
   * @throws IllegalArgumentException If the existing provider was not found or any of the
   *                                  specified data is wrong (error message can be used in HTTP
   *                                  response)
   */
  public void update(Long id, Provider provider) {
    Optional<Provider> existingprovider = this.providerRepository.findById(id);
    if (existingprovider.isEmpty()) {
      throw new IllegalArgumentException("Provider not found");
    }
    if (!provider.isValid()) {
      throw new IllegalArgumentException("Provider is invalid");
    }
    this.providerRepository.save(provider);
  }
}
