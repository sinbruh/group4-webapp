package no.ntnu.project.group4.webapp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.ntnu.project.group4.webapp.models.User;
import no.ntnu.project.group4.webapp.repositories.UserRepository;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  /**
   * Get all users in the database.
   * 
   * @return All users in the database
   */
  public Iterable<User> getAll() {
    return this.userRepository.findAll();
  }

  /**
   * Try to find a user with the given ID regardless of if it exists or not
   * 
   * @param id The given ID
   * @return A user with the given ID regardless of if it exists or not
   */
  public Optional<User> getOne(Long id) {
    return this.userRepository.findById(id);
  }

  /**
   * Try to find a user with the given email regardless of if it exists or not.
   * 
   * @param email The given email
   * @return A user with the given email regardless of if it exists or not
   */
  public Optional<User> getOneByEmail(String email) {
    return this.userRepository.findByEmail(email);
  }

  /**
   * Add the given user to the database.
   * 
   * @param user The given user
   * @return The ID of the given user
   * @throws IllegalArgumentException If the given user is invalid
   */
  public Long add(User user) {
    if (!user.isValid()) {
      throw new IllegalArgumentException("User is invalid");
    }
    this.userRepository.save(user);
    return user.getId();
  }

  /**
   * Try to delete a user with the given ID.
   * 
   * @param id The given ID
   * @return True if the user was found and thus deleted or false otherwise
   */
  public boolean delete(Long id) {
    Optional<User> user = this.userRepository.findById(id);
    if (user.isPresent()) {
      this.userRepository.deleteById(id);
    }
    return user.isPresent();
  }

  /**
   * Try to update a user with the given ID.
   * 
   * @param id The given ID
   * @param user The updated user metadata
   * @throws IllegalArgumentException If the current user is not found or the updated user metadata
   *                                  has an ID mismatch or is invalid
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
      this.userRepository.save(existingUserObj);
    }
    return existingUser.isPresent();
  }
}
