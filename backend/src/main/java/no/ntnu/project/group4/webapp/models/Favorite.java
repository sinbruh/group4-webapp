package no.ntnu.project.group4.webapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity(name = "favorite")
public class Favorite {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  private User user;
  @ManyToOne
  private Configuration configuration;

  /**
   * Empty constructor needed for JPA.
   */
  public Favorite() {
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Configuration getConfiguration() {
    return this.configuration;
  }

  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }
}
