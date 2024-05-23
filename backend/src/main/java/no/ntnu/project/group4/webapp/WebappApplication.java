package no.ntnu.project.group4.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

/**
 * The WebappApplication class represents the application runner class for the application.
 *
 * @author Group 4
 * @version v1.0 (2024.05.22)
 */
@SpringBootApplication
public class WebappApplication {
  /**
   * Is the main starting point of the application. The operating system on the computer expects to
   * find a publicly available method it can call without having to create an instance of a class
   * first.
   * 
   * <p>This method is standardized across programming languages and operating systems and must
   * have the method signature given below.</p>
   *
   * @param args A fixed sized array of strings holding arguments provided from the command line
   *             during the startup of the application
   */
  public static void main(String[] args) {
    SpringApplication springApplication = new SpringApplication(WebappApplication.class);
    springApplication.addListeners(new ApplicationPidFileWriter("webapp.pid"));
    SpringApplication.run(WebappApplication.class, args);
  }
}
