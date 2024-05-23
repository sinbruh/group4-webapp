package no.ntnu.project.group4.webapp.services;

import java.util.Optional;
import no.ntnu.project.group4.webapp.models.Configuration;
import no.ntnu.project.group4.webapp.repositories.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The ConfigurationService class represents the service class for the configuration entity.
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
@Service
public class ConfigurationService {
  @Autowired
  private ConfigurationRepository configurationRepository;

  /**
   * Returns all configurations in the database.
   *
   * @return All configurations in the database
   */
  public Iterable<Configuration> getAll() {
    return this.configurationRepository.findAll();
  }
  
  /**
   * Returns the configuration with the specified ID regardless of if it exists or not.
   *
   * @param id The specified ID
   * @return The configuration with the specified ID regardless of if it exists or not
   */
  public Optional<Configuration> getOne(Long id) {
    return this.configurationRepository.findById(id);
  }

  /**
   * Returns the generated ID of the specified configuration if it is added to the database.
   *
   * @param configuration The specified configuration
   * @return The generated ID of the specified configuration if it is added to the database
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
   * Returns true if the configuration with the specified ID is found and updated with the
   * specified configuration or false otherwise.
   *
   * @param id     The specified ID
   * @param config The specified configuration
   * @return True if the configuration with the specified ID is found and updated with the
   *         specified configuration or false otherwise
   * @throws IllegalArgumentException If the specified configuration is invalid
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
      existingConfigObj.setTransmissionType(config.getTransmissionType());
      existingConfigObj.setNumberOfSeats(config.getNumberOfSeats());
      this.configurationRepository.save(existingConfigObj);
    }
    return existingConfig.isPresent();
  }
  
  /**
   * Returns true if the configuration with the specified ID is found and deleted or false
   * otherwise.
   *
   * @param id The specified ID
   * @return True if the configuration with the specified ID is found and deleted or false
   *         otherwise
   */
  public boolean delete(Long id) {
    Optional<Configuration> configuration = this.configurationRepository.findById(id);
    if (configuration.isPresent()) {
      this.configurationRepository.deleteById(id);
    }
    return configuration.isPresent();
  }
}
