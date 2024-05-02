package no.ntnu.project.group4.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.ntnu.project.group4.webapp.models.Configuration;
import no.ntnu.project.group4.webapp.models.Rental;
import no.ntnu.project.group4.webapp.models.User;
import no.ntnu.project.group4.webapp.services.AccessUserService;
import no.ntnu.project.group4.webapp.services.ConfigurationService;
import no.ntnu.project.group4.webapp.services.RentalService;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/rentals")
public class RentalController {
  @Autowired
  private RentalService rentalService;
  @Autowired
  private AccessUserService userService;
  @Autowired
  private ConfigurationService configurationService;

  @GetMapping("/get")
  public Iterable<Rental> getAll() {
    return rentalService.getAll();
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<?> get(@PathVariable Long id) {
    ResponseEntity<?> response;
    User sessionUser = userService.getSessionUser();
    Optional<Rental> rental = rentalService.getOne(id);
    if (rental.isPresent()) {
      Rental foundRental = rental.get();
      if (sessionUser != null && sessionUser.getEmail().equals(foundRental.getUser().getEmail())) {
        response = new ResponseEntity<>(foundRental, HttpStatus.OK);
      } else if (sessionUser == null) {
        response = new ResponseEntity<>("Rental data accessible only to authenticated users",
                                        HttpStatus.UNAUTHORIZED);
      } else {
        response = new ResponseEntity<>("Rental data for other users not accessible",
                                        HttpStatus.FORBIDDEN);
      }
    } else {
      response = new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Adds the specified rental to the session user and the configuration with the specified ID in
   * the database.
   * 
   * @param id The specified ID
   * @param rental The specified rental
   * @return 201 CREATED on success or 400 BAD REQUEST or 404 NOT FOUND on error
   */
  @PostMapping("/add/configurations/{id}")
  public ResponseEntity<String> addRental(@PathVariable Long id, @RequestBody Rental rental) {
    ResponseEntity<String> response;
    Optional<Configuration> configuration = this.configurationService.getOne(id);
    if (configuration.isPresent()) {
      rental.setUser(this.userService.getSessionUser());
      rental.setConfiguration(configuration.get());
      try {
        this.rentalService.add(rental);
        response = new ResponseEntity<>("", HttpStatus.CREATED);
      } catch (IllegalArgumentException e) {
        response = new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
      }
    } else {
      response = new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Deletes the rental with the specified ID from the database.
   * 
   * @param id The specified ID
   * @return 200 OK on success or 401 UNAUTHORIZED, 403 FORBIDDEN or 404 NOT FOUND on error
   */
  @DeleteMapping("/del/{id}")
  public ResponseEntity<String> deleteRental(@PathVariable Long id) {
    ResponseEntity<String> response;
    User sessionUser = userService.getSessionUser();
    Optional<Rental> rental = rentalService.getOne(id);
    if (rental.isPresent()) {
      if (sessionUser != null &&
          sessionUser.getEmail().equals(rental.get().getUser().getEmail())) {
        rentalService.delete(id);
        response = new ResponseEntity<>("", HttpStatus.OK);
      } else if (sessionUser == null) {
        response = new ResponseEntity<>("Rental data accessible only to authenticated users",
                                        HttpStatus.UNAUTHORIZED);
      } else {
        response = new ResponseEntity<>("Rental data for other users not accessible",
                                        HttpStatus.FORBIDDEN);
      }
    } else {
      response = new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }
    return response;
  }
}
