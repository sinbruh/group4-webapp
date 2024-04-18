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

@Entity(name = "car")
public class Car {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String make;
  private String model;
  private int year;
  @ManyToMany
  @JoinTable(
    name = "car_configuration",
    joinColumns = @JoinColumn(name = "car_id"),
    inverseJoinColumns = @JoinColumn(name = "configuration_id")
  )
  private Set<Configuration> configurations = new LinkedHashSet<>();

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
}
