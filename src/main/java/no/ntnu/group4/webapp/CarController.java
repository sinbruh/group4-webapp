package no.ntnu.group4.webapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class CarController {

  @GetMapping()
  public Iterable<Car> getAll() {
    return null;
  }
}
