package no.ntnu.project.group4.webapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity(name = "provider")
public class Provider {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private int price;
  @JsonIgnore
  @ManyToOne
  private Configuration configuration;

  /**
   * Empty constructor needed for JPA.
   */
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

  // public Configuration getConfiguration() {
  //   return this.configuration;
  // }

  // public void setConfiguration(Configuration configuration) {
  //   this.configuration = configuration;
  // }

  public boolean isValid() {
    return !this.name.isBlank() && this.price > 0;
  }
}
