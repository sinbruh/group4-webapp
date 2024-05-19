package no.ntnu.project.group4.webapp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.ntnu.project.group4.webapp.models.Configuration;
import no.ntnu.project.group4.webapp.repositories.ConfigurationRepository;

@Service
public class ConfigurationService {
  @Autowired
  private ConfigurationRepository configurationRepository;

  /**
   * Gets all configurations in the database.
   * 
   * @return All configurations in the database
   */
  public Iterable<Configuration> getAll() {
    return this.configurationRepository.findAll();
  }
  
  /**
   * Gets the configuration with the specified ID regardless of if it exists or not.
   * 
   * @param id The specified ID
   * @return The configuration with the specified ID regardless of if it exists or not
   */
  public Optional<Configuration> getOne(Long id) {
    return this.configurationRepository.findById(id);
  }

  /**
   * Tries to add the specified configuration to the database.
   * 
   * @param configuration The specified configuration
   * @return The ID of the specified configuration added to the database
   * @throws IllegalArgumentException If the specified configuration is invalid
   */
  public Long add(Configuration configuration) {
    if (!configuration.isValid()) {
      throw new IllegalArgumentException("Configuration is invalid");
    }
    this.configurationRepository.save(configuration);
    return configuration.getId();
  }
  
  /**
   * Tries to delete the configuration with the specified ID.
   * 
   * @param id The specified ID
   * @return True if the configuration with the specified ID was found and thus deleted or false
   *         otherwise
   */
  public boolean delete(Long id) {
    Optional<Configuration> configuration = this.configurationRepository.findById(id);
    if (configuration.isPresent()) {
      this.configurationRepository.deleteById(id);
    }
    return configuration.isPresent();
  }
  
  /**
   * Tries to update the configuration with the specified ID to the specified configuration. The ID
   * of the configuration must match the specified ID.
   * 
   * @param id The specified ID
   * @param config The specified configuration
   * @throws IllegalArgumentException If the existing configuration was not found or any of the
   *                                  specified data is wrong (error message can be used in HTTP
   *                                  response)
   */
  public boolean update(Long id, Configuration config) {
    Optional<Configuration> existingConfig = this.configurationRepository.findById(id);
    if (!config.isValid()) {
      throw new IllegalArgumentException("Configuration is invalid");
    }
    if (existingConfig.isPresent()) {
      Configuration existingConfigObj = existingConfig.get();
      existingConfigObj.setName(config.getName());
      existingConfigObj.setFuelType(config.getFuelType());
      existingConfigObj.setTransmissionType(config.getTranmissionType());
      existingConfigObj.setNumberOfSeats(config.getNumberOfSeats());
      this.configurationRepository.save(existingConfigObj);
    }
    return existingConfig.isPresent();
  }
}
