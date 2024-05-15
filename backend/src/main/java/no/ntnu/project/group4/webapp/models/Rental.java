package no.ntnu.project.group4.webapp.models;

import io.swagger.v3.oas.annotations.media.Schema;

import java.sql.Date;
import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity(name = "rental")
@Schema(name = "Rental", description = "A rental entity, representing a rental of a car configuration.")
public class Rental {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Date startDate;
  private Date endDate;
  private Time startTime;
  private Time endTime;
  @JsonIgnore
  @ManyToOne
  private Configuration configuration;
  @JsonIgnore
  @ManyToOne
  private User user;

  /**
   * Empty constructor needed for JPA.
   */
  public Rental() {
  }

  public Rental(Date startDate, Date endDate, Time startTime, Time endTime) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Getter for start date.
   * 
   * @return Start date
   */
  public Date getStartDate() {
    return this.startDate;
  }

  /**
   * Setter for start date.
   * 
   * @param startDate The specified start date
   */
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  /**
   * Getter for end date.
   * 
   * @return End date
   */
  public Date getEndDate() {
    return this.endDate;
  }

  /**
   * Setter for end date.
   * 
   * @param endDate The specified end date
   */
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
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
