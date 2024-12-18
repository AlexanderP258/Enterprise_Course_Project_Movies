package se.pumarin.eeproject.controller.internal;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import se.pumarin.eeproject.model.User;
import se.pumarin.eeproject.response.ErrorResponse;
import se.pumarin.eeproject.response.MessageResponse;
import se.pumarin.eeproject.response.Response;
import se.pumarin.eeproject.response.TokenResponse;
import se.pumarin.eeproject.service.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import se.pumarin.eeproject.dto.LoginRequest;
import se.pumarin.eeproject.security.JwtTokenUtil;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public UserController(UserServiceImpl userService, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
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

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        try {
            var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(auth);

            String token = jwtTokenUtil.generateToken(loginRequest.getUsername());
            return ResponseEntity.ok(new TokenResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Invalid credentials"));
        }
    }


    @DeleteMapping("/delete")
    public ResponseEntity<Response> deleteCurrentUser() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        if (currentUsername == null || currentUsername.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("User not authenticated"));
        }

        Optional<User> userOptional = userService.getUserByUsername(currentUsername);
        if (userOptional.isPresent()) {
            boolean deleted = userService.deleteUser(userOptional.get().getUserId());
            if (deleted) {
                return ResponseEntity.ok(new MessageResponse("User deleted successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ErrorResponse("Failed to delete user"));
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("User not found"));
    }



}
