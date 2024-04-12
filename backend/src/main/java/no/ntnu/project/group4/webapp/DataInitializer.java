package no.ntnu.project.group4.webapp;

import java.util.List;
import no.ntnu.project.group4.webapp.models.Role;
import no.ntnu.project.group4.webapp.repositories.RoleRepository;
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

  private final Logger logger = LoggerFactory.getLogger("DataInit");

  /**
   * This method is called when the application is ready (loaded).
   *
   * @param event Event which is not used
   */
  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    List<Role> existingRoles = roleRepository.findAll();
    if (existingRoles.isEmpty()) {
      logger.info("Importing data...");

      Role user = new Role("ROLE_USER");
      Role admin = new Role("ROLE_ADMIN");

      roleRepository.save(user);
      roleRepository.save(admin);

      logger.info("Done importing data");
    } else {
      logger.info("Roles already in the database, not importing data");
    }
  }
}
