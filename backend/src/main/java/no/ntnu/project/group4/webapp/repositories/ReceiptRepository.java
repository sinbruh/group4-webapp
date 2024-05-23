package no.ntnu.project.group4.webapp.repositories;

import no.ntnu.project.group4.webapp.models.Receipt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The ReceiptRepository class represents the repository class for the receipt entity.  
 *
 * @author Group 4
 * @version v1.0 (2024.05.20)
 */
@Repository
public interface ReceiptRepository extends CrudRepository<Receipt, Long> {
}
