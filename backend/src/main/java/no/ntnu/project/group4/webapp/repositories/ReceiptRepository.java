package no.ntnu.project.group4.webapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import no.ntnu.project.group4.webapp.models.Receipt;

@Repository
public interface ReceiptRepository extends CrudRepository<Receipt, Long> {
}
