package no.ntnu.project.group4.webapp.models;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity(name = "rental")
public class Rental {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  private Car car;
  @ManyToOne
  private User user;
  private Date startDate;
  private Date endDate;

  public Rental() {
  }

  public Rental(Car car, User user, Date startDate, Date endDate) {
    this.car = car;
    this.user = user;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Car getCar() {
    return this.car;
  }

  public void setCar(Car car) {
    this.car = car;
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Date getStartDate() {
    return this.startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return this.endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public boolean isValid() {
    return this.car != null && this.user != null && this.startDate != null && this.endDate != null;
  }
}
