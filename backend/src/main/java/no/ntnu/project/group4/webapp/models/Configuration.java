package no.ntnu.project.group4.webapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Configuration {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @ManyToOne
  private Model model;

  public Configuration() {
  }

  public Configuration(String name) {
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

  public Model getModel() {
    return this.model;
  }

  public void setModel(Model model) {
    this.model = model;
  }
}
