package no.ntnu.group4.webapp.controllers;

import no.ntnu.group4.webapp.model.Rental;
import no.ntnu.group4.webapp.service.RentalService;
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
}
