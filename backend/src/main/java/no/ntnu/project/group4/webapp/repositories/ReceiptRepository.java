package no.ntnu.project.group4.webapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import no.ntnu.project.group4.webapp.models.Receipt;

/**
 * The ReceiptRepository class represents the repository class for the receipt entity.  
 * 
 * @author Group 4
 * @version v1.0 (2024.05.20)
 */
@Repository
public interface ReceiptRepository extends CrudRepository<Receipt, Long> {
}
