package no.ntnu.project.group4.webapp.services;

import java.util.Optional;
import no.ntnu.project.group4.webapp.models.User;
import no.ntnu.project.group4.webapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The UserService class represents the service class for the user entity.
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  /**
   * Returns all users in the database.
   *
   * @return All users in the database
   */
  public Iterable<User> getAll() {
    return this.userRepository.findAll();
  }

  /**
   * Returns the user with the specified ID regardless of if it exists or not.
   *
   * @param id The specified ID
   * @return The user with the specified ID regardless of if it exists or not
   */
  public Optional<User> getOne(Long id) {
    return this.userRepository.findById(id);
  }

  /**
   * Returns the user with the specified email regardless of if it exists or not.
   *
   * @param email The specified email
   * @return The user with the specified email regardless of if it exists or not
   */
  public Optional<User> getOneByEmail(String email) {
    return this.userRepository.findByEmail(email);
  }

  /**
   * Returns the generated ID of the specified user if it is added to the database.
   *
   * @param user The specified user
   * @return The generated ID of the specified user if it is added to the database
   * @throws IllegalArgumentException If the specified user is invalid
   */
  public Long add(User user) {
    if (!user.isValid()) {
      throw new IllegalArgumentException("User is invalid");
    }
    this.userRepository.save(user);
    return user.getId();
  }

  /**
   * Returns true if the user with the specified ID is found and updated with the specified user or
   * false otherwise.
   *
   * @param id   The specified ID
   * @param user The specified user
   * @return True if the user with the specified ID is found and updated with the specified
   *         user or false otherwise
   * @throws IllegalArgumentException If the specified user is invalid
   */
  public boolean update(Long id, User user) {
    Optional<User> existingUser = this.userRepository.findById(id);
    if (!user.isValid()) {
      throw new IllegalArgumentException("User is invalid");
    }
    if (existingUser.isPresent()) {
      User existingUserObj = existingUser.get();
      existingUserObj.setFirstName(user.getFirstName());
      existingUserObj.setLastName(user.getLastName());
      existingUserObj.setEmail(user.getEmail());
      existingUserObj.setPhoneNumber(user.getPhoneNumber());
      existingUserObj.setPassword(user.getPassword());
      existingUserObj.setDateOfBirth(user.getDateOfBirth());
      existingUserObj.setActive(user.isActive());
      existingUserObj.setRoles(user.getRoles());
      existingUserObj.setReceipts(user.getReceipts());
      existingUserObj.setFavorites(user.getFavorites());
      this.userRepository.save(existingUserObj);
    }
    return existingUser.isPresent();
  }

  /**
   * Returns true if the user with the specified ID is found and deleted or false otherwise.
   *
   * @param id The specified ID
   * @return True if the user with the specified ID is found and deleted or false otherwise
   */
  public boolean delete(Long id) {
    Optional<User> user = this.userRepository.findById(id);
    if (user.isPresent()) {
      this.userRepository.deleteById(id);
    }
    return user.isPresent();
  }
}
