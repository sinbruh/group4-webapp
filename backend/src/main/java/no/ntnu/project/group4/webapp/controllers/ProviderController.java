package no.ntnu.project.group4.webapp.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.ntnu.project.group4.webapp.models.Provider;
import no.ntnu.project.group4.webapp.services.ProviderService;

@CrossOrigin
@RestController
@RequestMapping("/api/providers")
public class ProviderController {
  @Autowired
  private ProviderService providerService;

  @GetMapping("/get")
  public Iterable<Provider> getAll() {
    return this.providerService.getAll();
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<Provider> getOne(@PathVariable Long id) {
    ResponseEntity<Provider> response;
    Optional<Provider> provider = this.providerService.getOne(id);
    if (provider.isPresent()) {
      response = ResponseEntity.ok(provider.get());
    } else {
      response = ResponseEntity.notFound().build();
    }
    return response;
  }

  @DeleteMapping("/delete/{id}")
  public HttpStatus deleteOne(@PathVariable Long id) {
    HttpStatus response;
    Optional<Provider> provider = this.providerService.getOne(id);
    if (provider.isPresent()) {
      this.providerService.delete(id);
      response = HttpStatus.ACCEPTED;
    } else {
      response = HttpStatus.NOT_FOUND;
    }
    return response;
  }
}
