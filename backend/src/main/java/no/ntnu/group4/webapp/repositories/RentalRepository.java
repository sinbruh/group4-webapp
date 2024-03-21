package src.main.java.no.ntnu.group4.webapp.repositories;

import src.main.java.no.ntnu.group4.webapp.model.Rental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends CrudRepository<Rental, Integer> {
}
