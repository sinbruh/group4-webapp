package no.ntnu.group4.webapp.repositories;

import no.ntnu.group4.webapp.model.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for SQL access to our database table.
 */
@Repository
public interface CarRepository extends CrudRepository<Car, Integer> {
}
