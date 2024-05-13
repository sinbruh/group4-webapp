package no.ntnu.project.group4.webapp.models;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity(name = "car")
@Schema(name = "Car", description = "A car entity")
public class Car {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String make;
  private String model;
  private int year;
  @OneToMany(mappedBy = "car")
  private Set<Configuration> configurations = new LinkedHashSet<>();

  /**
   * Empty constructor needed for JPA.
   */
  public Car() {
  }

  public Car(String make, String model, int year) {
    this.make = make;
    this.model = model;
    this.year = year;
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

  public String getModel() {
    return this.model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public int getYear() {
    return this.year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public Set<Configuration> getConfigurations() {
    return this.configurations;
  }

  public void setConfigurations(Set<Configuration> configurations) {
    this.configurations = configurations;
  }

  public boolean isValid() {
    return !this.make.isBlank() && !this.model.isBlank() && this.year >= 0;
  }
}
