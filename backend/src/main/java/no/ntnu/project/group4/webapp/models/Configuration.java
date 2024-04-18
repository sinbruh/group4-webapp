package no.ntnu.project.group4.webapp.models;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity(name = "configuration")
public class Configuration {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String fuelType;
  private String transmissionType;
  private int numberOfSeats;
  private String location;
  private boolean available;
  @ManyToOne
  private Car car;
  @ManyToMany
  @JoinTable(
    name = "configuration_extra_feature",
    joinColumns = @JoinColumn(name = "configuration_id"),
    inverseJoinColumns = @JoinColumn(name = "extra_feature_id")
  )
  private Set<ExtraFeature> extraFeatures = new LinkedHashSet<>();
  @ManyToMany
  @JoinTable(
    name = "configuration_provider",
    joinColumns = @JoinColumn(name = "configuration_id"),
    inverseJoinColumns = @JoinColumn(name = "provider_id")
  )
  private Set<Provider> providers = new LinkedHashSet<>();

  public Configuration() {
  }

  public Configuration(String name, String fuelType, String transmissionType, int numberOfSeats,
                       String location) {
    this.name = name;
    this.fuelType = fuelType;
    this.transmissionType = transmissionType;
    this.numberOfSeats = numberOfSeats;
    this.location = location;
    this.available = true;
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

  public String getFuelType() {
    return this.fuelType;
  }

  public void setFuelType(String fuelType) {
    this.fuelType = fuelType;
  }

  public String getTranmissionType() {
    return this.transmissionType;
  }

  public void setTransmissionType(String tranmissionType) {
    this.transmissionType = tranmissionType;
  }

  public int getNumberOfSeats() {
    return this.numberOfSeats;
  }

  public void setNumberOfSeats(int numberOfSeats) {
    this.numberOfSeats = numberOfSeats;
  }

  public String getLocation() {
    return this.location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public boolean isAvailable() {
    return this.available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  public Car getCar() {
    return this.car;
  }

  public void setCar(Car car) {
    this.car = car;
  }

  public Set<ExtraFeature> getExtraFeatures() {
    return this.extraFeatures;
  }

  public void setExtraFeatures(Set<ExtraFeature> extraFeatures) {
    this.extraFeatures = extraFeatures;
  }

  public Set<Provider> getProviders() {
    return this.providers;
  }

  public void setProviders(Set<Provider> providers) {
    this.providers = providers;
  }

  /**
   * Adds the specified extra feature to the configuration.
   * 
   * @param extraFeature The specified extra feature
   */
  public void addExtraFeature(ExtraFeature extraFeature) {
    this.extraFeatures.add(extraFeature);
  }

  /**
   * Adds the specified provider to the configuration.
   * 
   * @param provider The specified provider
   */
  public void addProvider(Provider provider) {
    this.providers.add(provider);
  }

  public boolean isValid() {
    return !this.name.isBlank() && !this.fuelType.isBlank() && !this.transmissionType.isBlank() &&
           this.numberOfSeats > 0 && !this.location.isBlank();
  }
}
