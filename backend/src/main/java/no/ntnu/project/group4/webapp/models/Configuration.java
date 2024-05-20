package no.ntnu.project.group4.webapp.models;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity(name = "configuration")
@Schema(name = "Configuration", description = "A configuration entitity, representing a specific car configuration." +
    "One car can have multiple configurations, each with different features and providers.")
public class Configuration {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String fuelType;
  private String transmissionType;
  private int numberOfSeats;
  @JsonIgnore
  @ManyToOne
  private Car car;
  @OneToMany(mappedBy = "configuration")
  private Set<ExtraFeature> extraFeatures = new LinkedHashSet<>();
  @OneToMany(mappedBy = "configuration")
  private Set<Provider> providers = new LinkedHashSet<>();

  /**
   * Empty constructor needed for JPA.
   */
  public Configuration() {
  }

  public Configuration(String name, String fuelType, String transmissionType, int numberOfSeats) {
    this.name = name;
    this.fuelType = fuelType;
    this.transmissionType = transmissionType;
    this.numberOfSeats = numberOfSeats;
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

  public boolean isValid() {
    return !this.name.isBlank() && !this.fuelType.isBlank() && !this.transmissionType.isBlank() &&
           this.numberOfSeats > 0;
  }
}
