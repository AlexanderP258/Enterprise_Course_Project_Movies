package se.pumarin.eeproject.service;

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

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        return Optional.of(userRepository.save(user));
    }


    @Override
    public Optional<List<MovieEntity>> getWatchlist(Long id) {
        return userRepository.findById(id)
                .map(User::getWatchlist);
    }
}
