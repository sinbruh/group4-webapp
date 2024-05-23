package no.ntnu.project.group4.webapp.services;

import java.util.Optional;
import no.ntnu.project.group4.webapp.models.Rental;
import no.ntnu.project.group4.webapp.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The RentalService class represents the service class for the rental entity.
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
@Service
public class RentalService {
  @Autowired
  private RentalRepository rentalRepository;

  /**
   * Returns all rentals in the database.
   *
   * @return All rentals in the database
   */
  public Iterable<Rental> getAll() {
    return this.rentalRepository.findAll();
  }

  /**
   * Returns the rental with the specified ID regardless of if it exists or not.
   *
   * @param id The specified ID
   * @return The rental with the specified ID regardless of if it exists or not
   */
  public Optional<Rental> getOne(Long id) {
    return this.rentalRepository.findById(id);
  }

  /**
   * Returns the generated ID of the specified rental if it is added to the database.
   *
   * @param rental The specified rental
   * @return The generated ID of the specified rental if it is added to the database
   * @throws IllegalArgumentException If the specified rental is invalid
   */
  public Long add(Rental rental) {
    if (!rental.isValid()) {
      throw new IllegalArgumentException("Rental is invalid");
    }
    this.rentalRepository.save(rental);
    return rental.getId();
  }

  /**
   * Returns true if the rental with the specified ID is found and updated with the specified
   * rental or false otherwise.
   *
   * @param id       The specified ID
   * @param rental The specified rental
   * @return True if the rental with the specified ID is found and updated with the specified
   *         rental or false otherwise
   * @throws IllegalArgumentException If the specified rental is invalid
   */
  public boolean update(Long id, Rental rental) {
    Optional<Rental> existingRental = this.rentalRepository.findById(id);
    if (!rental.isValid()) {
      throw new IllegalArgumentException("Rental is invalid");
    }
    if (existingRental.isPresent()) {
      Rental existingRentalObj = existingRental.get();
      existingRentalObj.setStartDate(rental.getStartDate().getTime());
      existingRentalObj.setEndDate(rental.getEndDate().getTime());
      this.rentalRepository.save(existingRentalObj);
    }
    return existingRental.isPresent();
  }

  /**
   * Returns true if the rental with the specified ID is found and deleted or false otherwise.
   *
   * @param id The specified ID
   * @return True if the rental with the specified ID is found and deleted or false otherwise
   */
  public boolean delete(Long id) {
    Optional<Rental> rental = this.rentalRepository.findById(id);
    if (rental.isPresent()) {
      this.rentalRepository.deleteById(id);
    }
    return rental.isPresent();
  }
}
