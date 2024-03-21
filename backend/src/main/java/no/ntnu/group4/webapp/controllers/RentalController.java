package src.main.java.no.ntnu.group4.webapp.controllers;

import src.main.java.no.ntnu.group4.webapp.model.Rental;
import src.main.java.no.ntnu.group4.webapp.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping ("/rentals")
public class RentalController {
    @Autowired
    private RentalService rentalService;

    @GetMapping()
    public Iterable<Rental> getAll() {
        return rentalService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getOne(@PathVariable int id) {
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
    public ResponseEntity<Void> deleteRental(@PathVariable int id) {
        boolean wasDeleted = rentalService.delete(id);
        if (wasDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
