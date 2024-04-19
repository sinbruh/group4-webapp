package no.ntnu.project.group4.webapp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.ntnu.project.group4.webapp.models.ExtraFeature;
import no.ntnu.project.group4.webapp.repositories.ExtraFeatureRepository;

@Service
public class ExtraFeatureService {
  @Autowired
  private ExtraFeatureRepository extraFeatureRepository;

  /**
   * Gets all extra features in the database.
   * 
   * @return All extra features in the database
   */
  public Iterable<ExtraFeature> getAll() {
    return this.extraFeatureRepository.findAll();
  }
  
  /**
   * Gets the extra feature with the specified ID regardless of if it exists or not.
   * 
   * @param id The specified ID
   * @return The extra feature with the specified ID regardless of if it exists or not
   */
  public Optional<ExtraFeature> getOne(Long id) {
    return this.extraFeatureRepository.findById(id);
  }

  /**
   * Tries to add the specified extra feature to the database.
   * 
   * @param extraFeature The specified extra feature
   * @return The ID of the specified extra feature added to the database
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
   * Tries to delete the extra feature with the specified ID.
   * 
   * @param id The specified ID
   * @return True if the extra feature with the specified ID was found and thus deleted or false
   *         otherwise
   */
  public boolean delete(Long id) {
    Optional<ExtraFeature> extraFeature = this.extraFeatureRepository.findById(id);
    if (extraFeature.isPresent()) {
      this.extraFeatureRepository.deleteById(id);
    }
    return extraFeature.isPresent();
  }
  
  /**
   * Tries to update the extra feature with the specified ID to the specified extra feature. The ID
   * of the extra feature must match the specified ID.
   * 
   * @param id The specified ID
   * @param extraFeature The specified extra feature
   * @throws IllegalArgumentException If the existing extra feature was not found or any of the
   *                                  specified data is wrong (error message can be used in HTTP
   *                                  response)
   */
  public void update(Long id, ExtraFeature extraFeature) {
    Optional<ExtraFeature> existingextraFeature = this.extraFeatureRepository.findById(id);
    if (existingextraFeature.isEmpty()) {
      throw new IllegalArgumentException("Extra feature not found");
    }
    if (extraFeature.getId() != id) {
      throw new IllegalArgumentException("ID mismatch");
    }
    if (!extraFeature.isValid()) {
      throw new IllegalArgumentException("Extra feature is invalid");
    }
    this.extraFeatureRepository.save(extraFeature);
  }
}
