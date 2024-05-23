package no.ntnu.project.group4.webapp.services;

import java.util.Optional;
import no.ntnu.project.group4.webapp.models.ExtraFeature;
import no.ntnu.project.group4.webapp.repositories.ExtraFeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The ExtraFeatureService class represents the service class for the extra feature entity.
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
@Service
public class ExtraFeatureService {
  @Autowired
  private ExtraFeatureRepository extraFeatureRepository;

  /**
   * Returns all extra features in the database.
   *
   * @return All extra features in the database
   */
  public Iterable<ExtraFeature> getAll() {
    return this.extraFeatureRepository.findAll();
  }
  
  /**
   * Returns the extra feature with the specified ID regardless of if it exists or not.
   *
   * @param id The specified ID
   * @return The extra feature with the specified ID regardless of if it exists or not
   */
  public Optional<ExtraFeature> getOne(Long id) {
    return this.extraFeatureRepository.findById(id);
  }

  /**
   * Returns the generated ID of the specified extra feature if it is added to the database.
   *
   * @param extraFeature The specified extra feature
   * @return The generated ID of the specified extra feature if it is added to the database
   * @throws IllegalArgumentException If the specified extra feature is invalid
   */
  public Long add(ExtraFeature extraFeature) {
    if (!extraFeature.isValid()) {
      throw new IllegalArgumentException("Extra feature is invalid");
    }
    this.extraFeatureRepository.save(extraFeature);
    return extraFeature.getId();
  }

  /**
   * Returns true if the extra feature with the specified ID is found and updated with the
   * specified extra feature or false otherwise.
   *
   * @param id           The specified ID
   * @param extraFeature The specified extra feature
   * @return True if the extra feature with the specified ID is found and updated with the
   *         specified extra feature or false otherwise
   * @throws IllegalArgumentException If the specified extra feature is invalid
   */
  public boolean update(Long id, ExtraFeature extraFeature) {
    Optional<ExtraFeature> existingExtraFeature = this.extraFeatureRepository.findById(id);
    if (!extraFeature.isValid()) {
      throw new IllegalArgumentException("Extra feature is invalid");
    }
    if (existingExtraFeature.isPresent()) {
      ExtraFeature existingExtraFeatureObj = existingExtraFeature.get();
      existingExtraFeatureObj.setName(extraFeature.getName());
      this.extraFeatureRepository.save(existingExtraFeatureObj);
    }
    return existingExtraFeature.isPresent();
  }
  
  /**
   * Returns true if the extra feature with the specified ID is found and deleted or false
   * otherwise.
   *
   * @param id The specified ID
   * @return True if the extra feature with the specified ID is found and deleted or false
   *         otherwise
   */
  public boolean delete(Long id) {
    Optional<ExtraFeature> extraFeature = this.extraFeatureRepository.findById(id);
    if (extraFeature.isPresent()) {
      this.extraFeatureRepository.deleteById(id);
    }
    return extraFeature.isPresent();
  }
}
