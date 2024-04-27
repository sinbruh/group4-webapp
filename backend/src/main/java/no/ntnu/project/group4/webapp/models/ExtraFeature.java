package no.ntnu.project.group4.webapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity(name = "extra_feature")
public class ExtraFeature {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
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
