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
 * The Receipt class represents the entity class for the receipt entity.
 *
 * <p>The class uses JPA with annotations for ORM operations.</p>
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
@Entity(name = "receipt")
@Schema(
    description = "A receipt entity, representing a specific receipt that can be added to a user"
)
public class Receipt {
  @Schema(description = "Unique ID")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Schema(description = "Name of rented car")
  private String carName;
  @Schema(description = "Name of provider rented from")
  private String providerName;
  @Schema(description = "Location of rented car")
  private String location;
  @Schema(description = "Start date of rental")
  private Date startDate;
  @Schema(description = "End date of rental")
  private Date endDate;
  @Schema(description = "Total price of rental")
  private int totalPrice;
  @Schema(description = "User the receipt belongs to")
  @JsonIgnore
  @ManyToOne
  private User user;

  /**
   * Constructs an instance of the Receipt class.
   *
   * <p>Empty constructor needed for JPA.</p>
   */
  public Receipt() {
    // Intentionally left blank
  }

  /**
   * Constructs an instance of the Receipt class.
   *
   * @param carName       The specified car name
   * @param providerName  The specified provider name
   * @param location      The specified location
   * @param startDateLong The specified long value for the start date
   * @param endDateLong   The specified long value for the end date
   * @param totalPrice    The specified total price
   */
  public Receipt(String carName, String providerName, String location, long startDateLong,
                 long endDateLong, int totalPrice) {
    this.carName = carName;
    this.providerName = providerName;
    this.location = location;
    this.startDate = new Date(startDateLong);
    this.endDate = new Date(endDateLong);
    this.totalPrice = totalPrice;
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
   * Getter for car name.
   *
   * @return Car name
   */
  public String getCarName() {
    return this.carName;
  }

  /**
   * Setter for car name.
   *
   * @param carName The specified car name
   */
  public void setCarName(String carName) {
    this.carName = carName;
  }

  /**
   * Getter for provider name.
   *
   * @return Provider name
   */
  public String getProviderName() {
    return this.providerName;
  }

  /**
   * Setter for provider name.
   *
   * @param providerName The specified provider name
   */
  public void setProviderName(String providerName) {
    this.providerName = providerName;
  }

  /**
   * Getter for location.
   *
   * @return Location
   */
  public String getLocation() {
    return this.location;
  }

  /**
   * Setter for location.
   *
   * @param location The specified location
   */
  public void setLocation(String location) {
    this.location = location;
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
   * Getter for total price.
   *
   * @return Total price
   */
  public int getTotalPrice() {
    return this.totalPrice;
  }

  /**
   * Setter for total price.
   *
   * @param totalPrice The specified total price
   */
  public void setTotalPrice(int totalPrice) {
    this.totalPrice = totalPrice;
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
   * Returns true if the receipt is valid or false otherwise.
   *
   * @return True if the receipt is valid or false otherwise
   */
  public boolean isValid() {
    return !this.carName.isBlank() && !this.providerName.isBlank() && !this.location.isBlank()
           && this.startDate != null && this.endDate != null && this.totalPrice >= 0;
  }
}
