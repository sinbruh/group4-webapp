package no.ntnu.project.group4.webapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import no.ntnu.project.group4.webapp.models.Car;

/**
 * Repository interface for SQL access to our database table.
 */
@Repository
public interface CarRepository extends CrudRepository<Car, Long> {
}
