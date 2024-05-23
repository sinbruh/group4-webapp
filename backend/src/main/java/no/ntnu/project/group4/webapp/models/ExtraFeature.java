package no.ntnu.project.group4.webapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * The ExtraFeature class represents the entity class for the extra feature entity.
 *
 * <p>The class uses JPA with annotations for ORM operations.</p>
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
@Entity(name = "extra_feature")
@Schema(
    description = "An extra feature entity, representing a specific configuration extra feature "
                + "that can be added to a car configuration"
)
public class ExtraFeature {
  @Schema(description = "Unique ID")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Schema(description = "Extra feature name")
  private String name;
  @Schema(description = "Configuration the extra feature belongs to")
  @JsonIgnore
  @ManyToOne
  private Configuration configuration;

  /**
   * Constructs an instance of the ExtraFeature class.
   *
   * <p>Empty constructor needed for JPA.</p>
   */
  public ExtraFeature() {
    // Intentionally left blank
  }

  /**
   * Constructs an instance of the ExtraFeature class.
   *
   * @param name The specified name
   */
  public ExtraFeature(String name) {
    this.name = name;
  }

  /**
   * Getter for ID.
   *
   * @return ID
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Setter for ID.
   *
   * @param id The specified ID
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Getter for name.
   *
   * @return Name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Setter for name.
   *
   * @param name The specified name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Getter for configuration.
   *
   * @return Configuration
   */
  public Configuration getConfiguration() {
    return this.configuration;
  }

  /**
   * Setter for configuration.
   *
   * @param configuration The specified configuration
   */
  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }

  /**
   * Returns true if the extra feature is valid or false otherwise.
   *
   * @return True if the extra feature is valid or false otherwise
   */
  public boolean isValid() {
    return !this.name.isBlank();
  }
}
