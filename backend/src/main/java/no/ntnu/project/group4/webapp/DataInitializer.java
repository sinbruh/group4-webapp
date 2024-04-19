package no.ntnu.project.group4.webapp;

import no.ntnu.project.group4.webapp.models.Car;
import no.ntnu.project.group4.webapp.models.Configuration;
import no.ntnu.project.group4.webapp.models.ExtraFeature;
import no.ntnu.project.group4.webapp.models.Provider;
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

  // TODO Refactor method
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
      Car car1 = new Car("Volkswagen", "Golf", 2007);
      Car car2 = new Car("Tesla", "Model 3", 2019);
      Car car3 = new Car("Tesla", "Model Y", 2022);
      Car car4 = new Car("Nissan", "Leaf", 2016);
      Car car5 = new Car("Mazda", "2", 2017);
      Car car6 = new Car("Volkswagen", "Transporter", 1978);
      Car car7 = new Car("BMW", "M3 Evo", 1988);
      Car car8 = new Car("Skoda", "Fabia", 2011);
      Car car9 = new Car("Peugeot", "307 SW", 2008);
      Car car10 = new Car("Peugeot", "207", 2007);
      Car car11 = new Car("Peugeot", "3008", 2010);
      Car car12 = new Car("Peugeot", "iOn", 2015);
      
      ExtraFeature exFeature1 = new ExtraFeature("Bluetooth");
      ExtraFeature exFeature2 = new ExtraFeature("DAB radio");
      ExtraFeature exFeature3 = new ExtraFeature("Warming in the chairs");
      ExtraFeature exFeature4 = new ExtraFeature("Autonomous driving");
      ExtraFeature exFeature5 = new ExtraFeature("Long range");
      ExtraFeature exFeature6 = new ExtraFeature("Warming in the seats");
      ExtraFeature exFeature7 = new ExtraFeature("Four wheel drive");
      ExtraFeature exFeature8 = new ExtraFeature("Glass roof");
      ExtraFeature exFeature9 = new ExtraFeature("Yellow");
      ExtraFeature exFeature10 = new ExtraFeature("Retro");
      ExtraFeature exFeature11 = new ExtraFeature("Three stripes");
      ExtraFeature exFeature12 = new ExtraFeature("Original tire discs");
      ExtraFeature exFeature13 = new ExtraFeature("Tow hook");
      ExtraFeature exFeature14 = new ExtraFeature("Travel box on the roof");
      ExtraFeature exFeature15 = new ExtraFeature("Glass window");
      ExtraFeature exFeature16 = new ExtraFeature("Warming in the steering wheel");
      ExtraFeature exFeature17 = new ExtraFeature("Warming in the mirrors");
      ExtraFeature exFeature18 = new ExtraFeature("Warming in the tires");
      ExtraFeature exFeature19 = new ExtraFeature("Warming under the rubber rugs");
      ExtraFeature exFeature20 = new ExtraFeature("Warming 360");
      ExtraFeature exFeature21 = new ExtraFeature("FM radio");
      ExtraFeature exFeature22 = new ExtraFeature("CD player");
      ExtraFeature exFeature23 = new ExtraFeature("Metallic paint");
      ExtraFeature exFeature24 = new ExtraFeature("Five doors");
      ExtraFeature exFeature25 = new ExtraFeature("Very economic");

      Provider provider1 = new Provider("Miller Bil", 600);
      Provider provider2 = new Provider("Biller Bil", 550);
      Provider provider3 = new Provider("Biggernes Tesla", 700);
      Provider provider4 = new Provider("Tesla Tom (private)", 500);
      Provider provider5 = new Provider("Biggernes Tesla", 900);
      Provider provider6 = new Provider("Tesla Tom (private)", 700);
      Provider provider7 = new Provider("Auto 9-9", 500);
      Provider provider8 = new Provider("Auto 10-10", 500);
      Provider provider9 = new Provider("Bilikist", 400);
      Provider provider10 = new Provider("Ørsta kommune", 200);
      Provider provider11 = new Provider("Sirkelsliper", 70);
      Provider provider12 = new Provider("Peace Per", 180);
      Provider provider13 = new Provider("Bilverksted", 400);
      Provider provider14 = new Provider("Grabes", 450);
      Provider provider15 = new Provider("Djarney", 449);
      Provider provider16 = new Provider("Sprekksaver", 300);
      Provider provider17 = new Provider("Smidig bilforhandler", 229);
      Provider provider18 = new Provider("Fossefall bilforhandler", 700);
      Provider provider19 = new Provider("Bertel Ostein", 600);
      Provider provider20 = new Provider("Auto 10-10", 550);
      Provider provider21 = new Provider("Bertel Ostein", 500);
      Provider provider22 = new Provider("Auto 10-10", 600);
      Provider provider23 = new Provider("Bertel Ostein", 200);
      Provider provider24 = new Provider("Auto 10-10", 201);

      Configuration config1 = new Configuration("Diesel 1", "Diesel", "Manual", 5,
                                                       "Ålesund");
      Configuration config2 = new Configuration("Electric 1", "Electric", "Automatic", 5,
                                                       "Ålesund");
      Configuration config3 = new Configuration("Electric 2", "Electric", "Automatic", 5,
                                                       "Ålesund");
      Configuration config4 = new Configuration("Electric 3", "Electric", "Automatic", 5,
                                                       "Ålesund");
      Configuration config5 = new Configuration("Petrol 1", "Petrol", "Automatic", 5,
                                                       "Ålesund");
      Configuration config6 = new Configuration("Petrol 2", "Petrol", "Manual", 8,
                                                       "Ålesund");
      Configuration config7 = new Configuration("Petrol 3", "Petrol", "Manual", 4,
                                                       "Ålesund");
      Configuration config8 = new Configuration("Diesel 2", "Diesel", "Automatic", 5,
                                                       "Ålesund");
      Configuration config9 = new Configuration("Diesel 3", "Diesel", "Manual", 7,
                                                       "Ålesund");
      Configuration config10 = new Configuration("Diesel 4", "Diesel", "Manual", 5,
                                                        "Ålesund");
      Configuration config11 = new Configuration("Diesel 5", "Diesel", "Manual", 5,
                                                        "Ålesund");
      Configuration config12 = new Configuration("Electric 4", "Electric", "Automatic", 4,
                                                        "Ålesund");

      config1.setCar(car1);
      config1.addExtraFeature(exFeature1);
      config1.addExtraFeature(exFeature2);
      config1.addExtraFeature(exFeature3);
      config1.addProvider(provider1);
      config1.addProvider(provider2);

      config2.setCar(car2);
      config2.addExtraFeature(exFeature4);
      config2.addExtraFeature(exFeature5);
      config2.addExtraFeature(exFeature6);
      config2.addProvider(provider3);
      config2.addProvider(provider4);

      config3.setCar(car3);
      config3.addExtraFeature(exFeature7);
      config3.addExtraFeature(exFeature8);
      config3.addExtraFeature(exFeature4);
      config3.addProvider(provider5);
      config3.addProvider(provider6);

      config4.setCar(car4);
      config4.addProvider(provider7);
      config4.addProvider(provider8);

      config5.setCar(car5);
      config5.addExtraFeature(exFeature2);
      config5.addProvider(provider9);

      config6.setCar(car6);
      config6.addExtraFeature(exFeature9);
      config6.addExtraFeature(exFeature10);
      config6.addProvider(provider10);
      config6.addProvider(provider11);
      config6.addProvider(provider12);

      config7.setCar(car7);
      config7.addExtraFeature(exFeature11);
      config7.addExtraFeature(exFeature12);
      config7.addProvider(provider13);
      config7.addProvider(provider14);
      config7.addProvider(provider15);

      config8.setCar(car8);
      config8.addExtraFeature(exFeature13);
      config8.addProvider(provider16);
      config8.addProvider(provider17);
      config8.addProvider(provider18);

      config9.setCar(car9);
      config9.addExtraFeature(exFeature14);
      config9.addProvider(provider19);
      config9.addProvider(provider20);

      config10.setCar(car10);
      config10.addExtraFeature(exFeature15);
      config10.addExtraFeature(exFeature3);
      config10.addExtraFeature(exFeature16);
      config10.addExtraFeature(exFeature17);
      config10.addExtraFeature(exFeature18);
      config10.addExtraFeature(exFeature19);
      config10.addExtraFeature(exFeature20);
      config10.addProvider(provider21);
      config10.addProvider(provider20);

      config11.setCar(car11);
      config11.addExtraFeature(exFeature21);
      config11.addExtraFeature(exFeature22);
      config11.addExtraFeature(exFeature23);
      config11.addProvider(provider19);
      config11.addProvider(provider22);

      config12.setCar(car12);
      config12.addExtraFeature(exFeature24);
      config12.addExtraFeature(exFeature25);
      config12.addProvider(provider23);
      config12.addProvider(provider24);

      this.carService.add(car1);
      this.carService.add(car2);
      this.carService.add(car3);
      this.carService.add(car4);
      this.carService.add(car5);
      this.carService.add(car6);
      this.carService.add(car7);
      this.carService.add(car8);
      this.carService.add(car9);
      this.carService.add(car10);
      this.carService.add(car11);
      this.carService.add(car12);

      this.extraFeatureService.add(exFeature1);
      this.extraFeatureService.add(exFeature2);
      this.extraFeatureService.add(exFeature3);
      this.extraFeatureService.add(exFeature4);
      this.extraFeatureService.add(exFeature5);
      this.extraFeatureService.add(exFeature6);
      this.extraFeatureService.add(exFeature7);
      this.extraFeatureService.add(exFeature8);
      this.extraFeatureService.add(exFeature9);
      this.extraFeatureService.add(exFeature10);
      this.extraFeatureService.add(exFeature11);
      this.extraFeatureService.add(exFeature12);
      this.extraFeatureService.add(exFeature13);
      this.extraFeatureService.add(exFeature14);
      this.extraFeatureService.add(exFeature15);
      this.extraFeatureService.add(exFeature16);
      this.extraFeatureService.add(exFeature17);
      this.extraFeatureService.add(exFeature18);
      this.extraFeatureService.add(exFeature19);
      this.extraFeatureService.add(exFeature20);
      this.extraFeatureService.add(exFeature21);
      this.extraFeatureService.add(exFeature22);
      this.extraFeatureService.add(exFeature23);
      this.extraFeatureService.add(exFeature24);
      this.extraFeatureService.add(exFeature25);

      this.providerService.add(provider1);
      this.providerService.add(provider2);
      this.providerService.add(provider3);
      this.providerService.add(provider4);
      this.providerService.add(provider5);
      this.providerService.add(provider6);
      this.providerService.add(provider7);
      this.providerService.add(provider8);
      this.providerService.add(provider9);
      this.providerService.add(provider10);
      this.providerService.add(provider11);
      this.providerService.add(provider12);
      this.providerService.add(provider13);
      this.providerService.add(provider14);
      this.providerService.add(provider15);
      this.providerService.add(provider16);
      this.providerService.add(provider17);
      this.providerService.add(provider18);
      this.providerService.add(provider19);
      this.providerService.add(provider20);
      this.providerService.add(provider21);
      this.providerService.add(provider22);
      this.providerService.add(provider23);
      this.providerService.add(provider24);

      this.configurationService.add(config1);
      this.configurationService.add(config2);
      this.configurationService.add(config3);
      this.configurationService.add(config4);
      this.configurationService.add(config5);
      this.configurationService.add(config6);
      this.configurationService.add(config7);
      this.configurationService.add(config8);
      this.configurationService.add(config9);
      this.configurationService.add(config10);
      this.configurationService.add(config11);
      this.configurationService.add(config12);

      this.logger.info("Done loading car data");
    } else {
      this.logger.info("Cars already in the database, not loading data");
    }
  }
}
