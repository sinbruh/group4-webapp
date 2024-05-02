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
import no.ntnu.project.group4.webapp.models.Provider;
import no.ntnu.project.group4.webapp.services.ConfigurationService;
import no.ntnu.project.group4.webapp.services.ProviderService;

@CrossOrigin
@RestController
@RequestMapping("/api/providers")
public class ProviderController {
  @Autowired
  private ProviderService providerService;
  @Autowired
  private ConfigurationService configurationService;

  @GetMapping
  public Iterable<Provider> getAll() {
    return this.providerService.getAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Provider> get(@PathVariable Long id) {
    ResponseEntity<Provider> response;
    Optional<Provider> provider = this.providerService.getOne(id);
    if (provider.isPresent()) {
      response = ResponseEntity.ok(provider.get());
    } else {
      response = ResponseEntity.notFound().build();
    }
    return response;
  }

  /**
   * Adds the specified provider for the configuration with the specified ID in the database.
   * 
   * @param id The specified ID
   * @param provider The specified provider
   * @return 201 CREATED on success or 400 BAD REQUEST or 404 NOT FOUND on error
   */
  @PostMapping("/configurations/{id}")
  public ResponseEntity<String> add(@PathVariable Long id,
                                    @RequestBody Provider provider) {
    ResponseEntity<String> response;
    Optional<Configuration> configuration = this.configurationService.getOne(id);
    if (configuration.isPresent()) {
      provider.setConfiguration(configuration.get());
      try {
        this.providerService.add(provider);
        response = new ResponseEntity<>("", HttpStatus.CREATED);
      } catch (Exception e) {
        response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
      }
    } else {
      response = new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }
    return response;
  }

  /**
   * Deletes the provider with the specified ID from the database.
   * 
   * @param id The specified ID
   * @return 200 OK on success or 404 NOT FOUND on error
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable Long id) {
    ResponseEntity<String> response;
    Optional<Provider> provider = this.providerService.getOne(id);
    if (provider.isPresent()) {
      this.providerService.delete(id);
      response = new ResponseEntity<>("", HttpStatus.OK);
    } else {
      response = new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }
    return response;
  }
}
