package no.ntnu.project.group4.webapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.sql.Date;

/**
 * The Rental class represents the entity class for the rental entity.
 *
 * <p>The class uses JPA with annotations for ORM operations.</p>
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
@Entity(name = "rental")
@Schema(
    description = "A rental entity, representing a specific rental that can be added to a user "
                + "and a configuration provider"
)
public class Rental {
  @Schema(description = "Unique ID")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Schema(description = "Start date of rental")
  private Date startDate;
  @Schema(description = "End date of rental")
  private Date endDate;
  @Schema(description = "Provider the rental belongs to")
  @JsonIgnore
  @ManyToOne
  private Provider provider;
  @Schema(description = "User the rental belongs to")
  @JsonIgnore
  @ManyToOne
  private User user;

  /**
   * Constructs an instance of the Rental class.
   *
   * <p>Empty constructor needed for JPA.</p>
   */
  public Rental() {
    // Intentionally left blank
  }

  /**
   * Constructs an instance of the Rental class.
   *
   * @param startDateLong The specified long value for the start date
   * @param endDateLong   The specified long value for the end date
   */
  public Rental(long startDateLong, long endDateLong) {
    this.startDate = new Date(startDateLong);
    this.endDate = new Date(endDateLong);
  }

  /**
   * Getter for ID.
   *
   * @return ID
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Setter for ID.
   *
   * @param id The specified ID
   */
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
   * @param startDateLong The specified long value for the start date
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
   * @param endDateLong The specified long value for the end date
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

  /**
   * Getter for user.
   *
   * @return User
   */
  public User getUser() {
    return this.user;
  }

  /**
   * Setter for user.
   *
   * @param user The specified user
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Returns true if the rental is valid or false otherwise.
   *
   * @return True if the rental is valid or false otherwise
   */
  public boolean isValid() {
    return this.startDate.getTime() > 0 && this.endDate.getTime() > 0;
  }
}
