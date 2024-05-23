package no.ntnu.project.group4.webapp.repositories;

import no.ntnu.project.group4.webapp.models.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The CarRepository class represents the repository class for the car entity.
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
@Repository
public interface CarRepository extends CrudRepository<Car, Long> {
}
