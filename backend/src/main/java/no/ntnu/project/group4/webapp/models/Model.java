package no.ntnu.project.group4.webapp.models;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity(name = "models")
public class Model {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String make;
  private String name;
  private int year;
  private String fuel;
  private String transmission;
  private int numberOfSeats;
  @OneToMany(mappedBy = "model")
  private Set<Configuration> configurations;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "model_feature",
    joinColumns = @JoinColumn(name = "model_id"),
    inverseJoinColumns = @JoinColumn(name = "feature_id")
  )
  private Set<ExtraFeature> extraFeatures = new LinkedHashSet<>();

  public Model() {
  }

  public Model(String make, String name, int year, String fuel, String transmission, int numberOfSeats) {
    this.make = make;
    this.name = name;
    this.year = year;
    this.fuel = fuel;
    this.transmission = transmission;
    this.numberOfSeats = numberOfSeats;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMake() {
    return this.make;
  }

  public void setMake(String make) {
    this.make = make;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getYear() {
    return this.year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public String getFuel() {
    return this.fuel;
  }

  public void setFuel(String fuel) {
    this.fuel = fuel;
  }

  public String getTransmission() {
    return this.transmission;
  }

  public void setTransmission(String transmission) {
    this.transmission = transmission;
  }

  public int getNumberOfSeats() {
    return this.numberOfSeats;
  }

  public void setNumberOfSeats(int numberOfSeats) {
    this.numberOfSeats = numberOfSeats;
  }

  public Set<Configuration> getConfigurations() {
    return this.configurations;
  }

  public void setConfigurations(Set<Configuration> configurations) {
    this.configurations = configurations;
  }

  public Set<ExtraFeature> getExtraFeatures() {
    return this.extraFeatures;
  }

  public void setExtraFeatures(Set<ExtraFeature> extraFeatures) {
    this.extraFeatures = extraFeatures;
  }

  /**
   * Adds the specified configuration to the model.
   * 
   * @param configuration The specified configuration
   */
  public void addConfiguration(Configuration configuration) {
    this.configurations.add(configuration);
  }

  /**
   * Adds the specified extra feature to the model.
   * 
   * @param extraFeature The specified extra feature
   */
  public void addExtraFeature(ExtraFeature extraFeature) {
    this.extraFeatures.add(extraFeature);
  }
}
