package no.ntnu.project.group4.webapp;

import no.ntnu.project.group4.webapp.models.Car;
import no.ntnu.project.group4.webapp.models.Role;
import no.ntnu.project.group4.webapp.repositories.CarRepository;
import no.ntnu.project.group4.webapp.repositories.RoleRepository;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * A class which inserts necessary data into the database, when Spring Boot app has started.
 */
@Component
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private CarRepository carRepository;

  private final Logger logger = LoggerFactory.getLogger("DataInit");

  /**
   * This method is called when the application is ready (loaded).
   * 
   * <p>Imports the initial data into the database. The initial data includes user roles and
   * cars.</p>
   *
   * @param event Event which is not used
   */
  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    logger.info("Importing data...");
    loadUserRoles();
    loadCars();
    logger.info("Done importing data");
  }

  /**
   * Loads the user roles into the database if it is not already there.
   */
  private void loadUserRoles() {
    boolean isEmpty = true; // Guard condition
    Iterable<Role> existingRoles = roleRepository.findAll();
    Iterator<Role> rolesIt = existingRoles.iterator();
    if (rolesIt.hasNext()) {
      isEmpty = false;
    }
    logger.info("Loading user role data...");
    if (isEmpty) {
      Role user = new Role("ROLE_USER");
      Role admin = new Role("ROLE_ADMIN");

      roleRepository.save(user);
      roleRepository.save(admin);

      logger.info("Done loading user role data");
    } else {
      logger.info("User roles already in the database, not loading data");
    }
  }

  /**
   * Loads the cars into the database if it is not already there.
   */
  private void loadCars() {
    boolean isEmpty = true; // Guard condition
    Iterable<Car> existingCars = carRepository.findAll();
    Iterator<Car> carsIt = existingCars.iterator();
    if (carsIt.hasNext()) {
      isEmpty = false;
    }
    logger.info("Loading car data...");
    if (isEmpty) {
      // TODO Construct car instances for each car and save it in the repository

      logger.info("Done loading car data");
    } else {
      logger.info("Cars already in the database, not loading data");
    }
  }
}
