package no.ntnu.project.group4.webapp.models;

import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Time;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity(name = "rental")
@Schema(name = "Rental", description = "A rental entity, representing a rental of a car " +
                                       "configuration.")
public class Rental {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Time startTime;
  private Time endTime;
  @ManyToOne
  private Configuration configuration;
  @ManyToOne
  private User user;

  /**
   * Empty constructor needed for JPA.
   */
  public Rental() {
  }

  public Rental(Time startTime, Time endTime) {
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Time getStartTime() {
    return this.startTime;
  }

  public void setStartTime(Time startTime) {
    this.startTime = startTime;
  }

  public Time getEndTime() {
    return this.endTime;
  }

  public void setEndTime(Time endTime) {
    this.endTime = endTime;
  }

  public Configuration getConfiguration() {
    return this.configuration;
  }

  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public boolean isValid() {
    return this.startTime != null && this.endTime != null;
  }
}
