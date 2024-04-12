package no.ntnu.project.group4.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.ntnu.project.group4.webapp.models.Rental;
import no.ntnu.project.group4.webapp.services.RentalService;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/rentals")
public class RentalController {
  @Autowired
  private RentalService rentalService;

  @GetMapping()
  public Iterable<Rental> getAll() {
    return rentalService.getAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Rental> getOne(@PathVariable Long id) {
    ResponseEntity<Rental> response;
    Optional<Rental> rental = rentalService.getOne(id);
    response = rental.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    return response;
  }

  @PostMapping()
  public ResponseEntity<Rental> addRental(@RequestBody Rental rental) {
    ResponseEntity<Rental> response;
    try {
      rentalService.add(rental);
      response = ResponseEntity.ok().build();
    } catch (IllegalArgumentException e) {
      response = ResponseEntity.badRequest().build();
    }
    return response;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
    boolean wasDeleted = rentalService.delete(id);
    if (wasDeleted) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
