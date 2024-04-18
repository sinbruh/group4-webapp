package no.ntnu.project.group4.webapp.models;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity(name = "provider")
public class Provider {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private int price;
  @ManyToMany(mappedBy = "providers")
  private Set<Configuration> configurations = new LinkedHashSet<>();

  public Provider() {
  }

  public Provider(String name, int price) {
    this.name = name;
    this.price = price;
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

  public int getPrice() {
    return this.price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public Set<Configuration> getConfigurations() {
    return this.configurations;
  }

  public void setConfigurations(Set<Configuration> configurations) {
    this.configurations = configurations;
  }
}
