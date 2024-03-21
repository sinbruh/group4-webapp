package no.ntnu.group4.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping ("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<User> addUser(@RequestBody User user) {
        ResponseEntity<User> response;
        try {
            int userId = userService.add(user);
            response = ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            response = ResponseEntity.badRequest().build();
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getOne(@PathVariable int id) {
        ResponseEntity<User> response;
        Optional<User> user = userService.getOne(id);
        response = user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        boolean wasDeleted = userService.delete(id);
        if (wasDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
