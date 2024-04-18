package no.ntnu.project.group4.webapp.models;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity(name = "extra_feature")
public class ExtraFeature {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @ManyToMany(mappedBy = "extraFeatures")
  private Set<Configuration> configurations = new LinkedHashSet<>();

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

  public Set<Configuration> getConfigurations() {
    return this.configurations;
  }

  public void setConfiguraitons(Set<Configuration> configurations) {
    this.configurations = configurations;
  }

  public boolean isValid() {
    return !this.name.isBlank();
  }
}
