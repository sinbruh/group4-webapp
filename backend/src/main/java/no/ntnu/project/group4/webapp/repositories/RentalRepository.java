package no.ntnu.project.group4.webapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import no.ntnu.project.group4.webapp.models.Rental;

/**
 * The RentalRepository class represents the repository class for the rental entity.
 * 
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
@Repository
public interface RentalRepository extends CrudRepository<Rental, Long> {
}
