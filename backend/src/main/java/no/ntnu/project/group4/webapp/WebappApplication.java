package no.ntnu.project.group4.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class  WebappApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(WebappApplication.class);
		springApplication.addListeners(new ApplicationPidFileWriter("webapp.pid"));
		SpringApplication.run(WebappApplication.class, args);
	}

}
