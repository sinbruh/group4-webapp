package no.ntnu.project.group4.webapp.models;

import io.swagger.v3.oas.annotations.media.Schema;

import java.sql.Date;

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
  @JsonIgnore
  @ManyToOne
  private Provider provider;
  @JsonIgnore
  @ManyToOne
  private User user;

  /**
   * Empty constructor needed for JPA.
   */
  public Rental() {
  }

  public Rental(long startDateLong, long endDateLong) {
    this.startDate = new Date(startDateLong);
    this.endDate = new Date(endDateLong);

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
   * @param startDate The specified long value for the start date
   */
  public void setStartDate(long startDateLong) {
    this.startDate = new Date(startDateLong);
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
   * @param endDate The specified long value for the end date
   */
  public void setEndDate(long endDateLong) {
    this.endDate = new Date(endDateLong);
  }

  /**
   * Getter for provider.
   * 
   * @return Provider
   */
  public Provider getProvider() {
    return this.provider;
  }

  /**
   * Setter for provider.
   * 
   * @param provider The specified provider
   */
  public void setProvider(Provider provider) {
    this.provider = provider;
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public boolean isValid() {
    return this.startDate.getTime() > 0 && this.endDate.getTime() > 0;
  }
}
