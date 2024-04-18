package no.ntnu.project.group4.webapp;

import no.ntnu.project.group4.webapp.models.Car;
import no.ntnu.project.group4.webapp.models.Configuration;
import no.ntnu.project.group4.webapp.models.Role;
import no.ntnu.project.group4.webapp.repositories.RoleRepository;
import no.ntnu.project.group4.webapp.services.CarService;
import no.ntnu.project.group4.webapp.services.ConfigurationService;
import no.ntnu.project.group4.webapp.services.ExtraFeatureService;
import no.ntnu.project.group4.webapp.services.ProviderService;

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
  private CarService carService;
  @Autowired
  private ConfigurationService configurationService;
  @Autowired
  private ExtraFeatureService extraFeatureService;
  @Autowired
  private ProviderService providerService;

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
    this.logger.info("Importing data...");
    this.loadUserRoles();
    this.loadCars();
    this.logger.info("Done importing data");
  }

  /**
   * Loads the user roles into the database if they are not already there.
   */
  private void loadUserRoles() {
    boolean isEmpty = true; // Guard condition
    Iterable<Role> existingRoles = this.roleRepository.findAll();
    Iterator<Role> rolesIt = existingRoles.iterator();
    if (rolesIt.hasNext()) {
      isEmpty = false;
    }
    this.logger.info("Loading user role data...");
    if (isEmpty) {
      Role user = new Role("ROLE_USER");
      Role admin = new Role("ROLE_ADMIN");

      this.roleRepository.save(user);
      this.roleRepository.save(admin);

      this.logger.info("Done loading user role data");
    } else {
      this.logger.info("User roles already in the database, not loading data");
    }
  }

  /**
   * Loads the cars into the database if they are not already there.
   */
  private void loadCars() {
    boolean isEmpty = true; // Guard condition
    Iterable<Car> existingCars = this.carService.getAll();
    Iterator<Car> carsIt = existingCars.iterator();
    if (carsIt.hasNext()) {
      isEmpty = false;
    }
    this.logger.info("Loading car data...");
    if (isEmpty) {
      Car car1 = new Car("Volkswagen", "Golf", 2007); // con1
      Car car2 = new Car("Tesla", "Model 3", 2019); // con2
      Car car3 = new Car("Tesla", "Model Y", 2022); // con3
      Car car4 = new Car("Nissan", "Leaf", 2016);
      Car car5 = new Car("Mazda", "2", 2017);
      Car car6 = new Car("Volkswagen", "Transporter", 1978);
      Car car7 = new Car("BMW", "M3 Evo", 1988);
      Car car8 = new Car("Skodia", "Fabia", 2011);
      Car car9 = new Car("Peugeot", "307 SW", 2008);
      Car car10 = new Car("Peugeot", "207", 2007);
      Car car11 = new Car("Peugeot", "3008", 2010);
      Car car12 = new Car("Peugeot", "iOn", 2015);

      Configuration configuration1 = new Configuration("Diesel 1", "Diesel", "Manual", 5,
                                                       "Ålesund");
      Configuration configuration2 = new Configuration("Electric 1", "Electric", "Automatic", 5,
                                                       "Ålesund");
      Configuration configuration3 = new Configuration("Electric 2", "Electric", "Automatic", 5,
                                                       "Ålesund");
      Configuration configuration4 = new Configuration("Electric 2", "Electric", "Automatic", 5,
                                                       "Ålesund");
      Configuration configuration5 = new Configuration("Petrol 1", "Petrol", "Automatic", 5,
                                                       "Ålesund");

      this.logger.info("Done loading car data");
    } else {
      this.logger.info("Cars already in the database, not loading data");
    }
  }
}
