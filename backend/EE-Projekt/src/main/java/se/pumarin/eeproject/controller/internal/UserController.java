package se.pumarin.eeproject.controller.internal;

import jakarta.validation.Valid;
import se.pumarin.eeproject.model.User;
import se.pumarin.eeproject.response.ErrorResponse;
import se.pumarin.eeproject.response.Response;
import se.pumarin.eeproject.service.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Response> getUser(@PathVariable Long id) {
        Optional<User> userOptional = userService.getUserById(id);

        if(userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get() );
        } else
            return ResponseEntity.status(404).body(new ErrorResponse("No user exists wih id: " + id));
    }

    @PostMapping("/create/newUser")
    public ResponseEntity<Response> newUser(@Valid @RequestBody User user) {
        Optional<User> createdUser = userService.createUser(user);

        if (createdUser.isPresent()) {
            return ResponseEntity.status(201)
                    .body(createdUser.get());
        } else {
            return ResponseEntity
                    .status(409)
                    .body(new ErrorResponse("Could not create new user"));
        }

    }

}
