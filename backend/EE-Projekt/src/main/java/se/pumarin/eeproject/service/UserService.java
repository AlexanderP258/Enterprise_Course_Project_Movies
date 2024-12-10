package se.pumarin.eeproject.service;

import se.pumarin.eeproject.model.User;
import se.pumarin.eeproject.model.movie.MovieEntity;

import java.util.List;
import java.util.Optional;


public interface UserService {

    public Optional<User> getUserById (Long id);
    public Optional<User> getUserByUsername (String name);
    public Optional<User> createUser(User user);
    public Optional<List<MovieEntity>> getWatchlist(Long id);
    public boolean deleteUser(Long id);


}
