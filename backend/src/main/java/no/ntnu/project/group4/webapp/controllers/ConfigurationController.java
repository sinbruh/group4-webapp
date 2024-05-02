package no.ntnu.project.group4.webapp.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.ntnu.project.group4.webapp.models.Configuration;
import no.ntnu.project.group4.webapp.services.ConfigurationService;

@CrossOrigin
@RestController
@RequestMapping("/api/configurations")
public class ConfigurationController {
  @Autowired
  private ConfigurationService configurationService;

  @GetMapping("/get")
  public Iterable<Configuration> getAll() {
    return this.configurationService.getAll();
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<Configuration> get(@PathVariable Long id) {
    ResponseEntity<Configuration> response;
    Optional<Configuration> configuration = this.configurationService.getOne(id);
    if (configuration.isPresent()) {
      response = ResponseEntity.ok(configuration.get());
    } else {
      response = ResponseEntity.notFound().build();
    }
    return response;
  }

  /**
   * Adds the specified configuration to the database.
   * 
   * @param configuration The specified configuration
   * @return 201 CREATED on success or 400 BAD REQUEST on error
   */
  @PostMapping("/add/")
  public ResponseEntity<String> add(@RequestBody Configuration configuration) {
    ResponseEntity<String> response;
    try {
      this.configurationService.add(configuration);
      response = new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return response;
  }

  /**
   * Deletes the configuration with the specified ID from the database.
   * 
   * @param id The specified ID
   * @return 200 OK on success or 404 NOT FOUND on error
   */
  @DeleteMapping("/del/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id) {
    ResponseEntity<String> response;
    Optional<Configuration> configuration = this.configurationService.getOne(id);
    if (configuration.isPresent()) {
      this.configurationService.delete(id);
      response = new ResponseEntity<>(HttpStatus.OK);
    } else {
      response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return response;
  }
}
