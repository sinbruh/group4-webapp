package no.ntnu.project.group4.webapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity(name = "extra_feature")
@Schema(name = "ExtraFeature", description = "An extra feature entity, representing an extra feature that can be added to a car configuration.")
public class ExtraFeature {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @JsonIgnore
  @ManyToOne
  private Configuration configuration;

  /**
   * Empty constructor needed for JPA.
   */
  public ExtraFeature() {
  }

  public ExtraFeature(String name) {
    this.name = name;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Configuration getConfiguration() {
    return this.configuration;
  }

  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }

  public boolean isValid() {
    return !this.name.isBlank();
  }
}
