package no.ntnu.group4.webapp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalService {
  @Autowired
  private RentalRepository rentalRepository;

  /**
   * Get all rentals in the database.
   * 
   * @return All rentals in the database
   */
  public Iterable<Rental> getAll() {
    return rentalRepository.findAll();
  }

  /**
   * Try to find a rental with the given ID regardless of if it exits or not
   * 
   * @param id The given ID
   * @return A rental with the given ID regardless of if it exists or not
   */
  public Optional<Rental> getOne(int id) {
    return rentalRepository.findById(id);
  }

  /**
   * Add the given rental in the database.
   * 
   * @param rental The given rental
   * @return The ID of the given rental
   * @throws IllegalArgumentException If the given rental is invalid
   */
  public int add(Rental rental) {
    if (!rental.isValid()) {
      throw new IllegalArgumentException("Rental is invalid");
    }
    rentalRepository.save(rental);
    return rental.getId();
  }

  /**
   * Try to delete a rental with the given ID.
   * 
   * @param id The given ID
   * @return True if the car was found and thus deleted or false otherwise
   */
  public boolean delete(int id) {
    Optional<Rental> rental = rentalRepository.findById(id);
    if (rental.isPresent()) {
      rentalRepository.deleteById(id);
    }
    return rental.isPresent();
  }

  /**
   * Try to update a rental with the given ID.
   * 
   * @param id The given ID
   * @param rental The updated rental metadata
   * @throws IllegalArgumentException If the current rental is not found or the updated rental
   *                                  metadata has an ID mismatch or is invalid
   */
  public void update(int id, Rental rental) {
    Optional<Rental> currentRental = rentalRepository.findById(id);
    if (!currentRental.isPresent()) {
      throw new IllegalArgumentException("Rental not found");
    }
    if (rental.getId() != id) {
      throw new IllegalArgumentException("ID mismatch");
    }
    if (!rental.isValid()) {
      throw new IllegalArgumentException("Rental is invalid");
    }
    rentalRepository.save(rental);
  }
}
