package se.pumarin.eeproject.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import se.pumarin.eeproject.model.User;
import se.pumarin.eeproject.model.movie.MovieEntity;
import se.pumarin.eeproject.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        try {
            return userRepository.findById(id);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return Optional.of(userRepository.save(user));
    }


    @Override
    public Optional<List<MovieEntity>> getWatchlist(Long id) {
        return userRepository.findById(id)
                .map(User::getWatchlist);
    }
}
