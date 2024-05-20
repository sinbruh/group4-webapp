package no.ntnu.project.group4.webapp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.ntnu.project.group4.webapp.models.Receipt;
import no.ntnu.project.group4.webapp.repositories.ReceiptRepository;

/**
 * The ReceiptService class represents the service class for the receipt entity.
 * 
 * @author Group 4
 * @version 1.0 (2024.05.20)
 */
@Service
public class ReceiptService {
  @Autowired
  private ReceiptRepository receiptRepository;

  /**
   * Returns the generated ID of the specified receipt if it is added to the database.
   * 
   * @param receipt The specified receipt
   * @return The generated ID of the specified receipt if it is added to the database
   * @throws IllegalArgumentException If the specified receipt is invalid
   */
  public Long add(Receipt receipt) {
    if (!receipt.isValid()) {
      throw new IllegalArgumentException("The specified receipt is invalid");
    }
    this.receiptRepository.save(receipt);
    return receipt.getId();
  }

  /**
   * Returns true if the receipt with the specified ID is found and deleted or false otherwise.
   * 
   * @param id The specified ID
   * @return True if the receipt with the specified ID is found and deleted or false otherwise
   */
  public boolean delete(Long id) {
    Optional<Receipt> receipt = this.receiptRepository.findById(id);
    if (receipt.isPresent()) {
      this.receiptRepository.delete(receipt.get());
    }
    return receipt.isPresent();
  }
}
