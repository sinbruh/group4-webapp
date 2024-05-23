package no.ntnu.project.group4.webapp.repositories;

import no.ntnu.project.group4.webapp.models.Rental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The RentalRepository class represents the repository class for the rental entity.
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
@Repository
public interface RentalRepository extends CrudRepository<Rental, Long> {
}
