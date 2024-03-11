package no.ntnu.group4.webapp;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class CarController {
  @Autowired
  private CarService carService;

  @GetMapping()
  public Iterable<Car> getAll() {
    return carService.getAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Car> getOne(int id) {
    ResponseEntity<Car> response;
    Optional<Car> car = carService.findById(id);
    if (car.isPresent()) {
      response = ResponseEntity.ok(car.get());
    } else {
      response = ResponseEntity.notFound().build();
    }
    return response;
  }
}
