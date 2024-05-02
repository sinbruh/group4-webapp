package no.ntnu.project.group4.webapp.models;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
  private boolean available = true;
  @ManyToOne
  private Car car;
  @ManyToMany(mappedBy = "favorites")
  private Set<User> favoritedUsers = new LinkedHashSet<>();

  /**
   * Empty constructor needed for JPA.
   */
  public Configuration() {
  }

  public Configuration(String name, String fuelType, String transmissionType, int numberOfSeats,
                       String location) {
    this.name = name;
    this.fuelType = fuelType;
    this.transmissionType = transmissionType;
    this.numberOfSeats = numberOfSeats;
    this.location = location;
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

  public Set<User> getFavoritedUsers() {
    return this.favoritedUsers;
  }

  public void setFavoritedUsers(Set<User> favoritedUsers) {
    this.favoritedUsers = favoritedUsers;
  }

  public boolean isValid() {
    return !this.name.isBlank() && !this.fuelType.isBlank() && !this.transmissionType.isBlank() &&
           this.numberOfSeats > 0 && !this.location.isBlank();
  }
}
