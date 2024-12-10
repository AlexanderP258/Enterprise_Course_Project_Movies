package se.pumarin.eeproject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.pumarin.eeproject.model.User;
import se.pumarin.eeproject.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class UserServiceImplTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userService = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    void testUserCreationAndRetrieval() {

        // 1. Test creating a new user successfully
        User newUser = new User("test@example.com", "password");
        when(userRepository.findByUsername("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setUserId(1L);
            return savedUser;
        });

        Optional<User> created = userService.createUser(newUser);

        // Asserts for creation scenario
        assertTrue(created.isPresent(), "User should be created");            // assert #1
        assertEquals(1L, created.get().getUserId(), "User ID should be 1");   // assert #2
        assertEquals("test@example.com", created.get().getUsername(), "Username should match"); // assert #3
        assertEquals("encodedPassword", created.get().getPassword(), "Password should be encoded"); // assert #4

        // 2. Test trying to create a duplicate user
        when(userRepository.findByUsername("test@example.com")).thenReturn(created);
        Optional<User> duplicateCreation = userService.createUser(new User("test@example.com", "newpass"));
        assertTrue(duplicateCreation.isEmpty(), "User should not be created if username already exists"); // assert #5

        // 3. Test retrieving a user by ID
        when(userRepository.findById(1L)).thenReturn(created);
        Optional<User> foundById = userService.getUserById(1L);
        assertTrue(foundById.isPresent(), "User should be found by ID");      // assert #6
        assertEquals("test@example.com", foundById.get().getUsername(), "Found user should have the correct username"); // assert #7

        // 4. Test retrieving a non-existent user by ID
        when(userRepository.findById(999L)).thenReturn(Optional.empty());
        Optional<User> notFoundUser = userService.getUserById(999L);
        assertTrue(notFoundUser.isEmpty(), "User should not be found for a non-existent ID"); // assert #8

        // 5. Test deleting a user
        when(userRepository.findById(1L)).thenReturn(created);
        doNothing().when(userRepository).deleteById(1L);
        boolean deleteResult = userService.deleteUser(1L);
        assertTrue(deleteResult, "User deletion should return true on success"); // assert #9
        verify(userRepository, times(1)).deleteById(1L);

        // 6. Test deleting a non-existent user
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        boolean deleteNonExistent = userService.deleteUser(2L);
        assertFalse(deleteNonExistent, "User deletion should return false if user not found"); // assert #10
    }
}

