package se.pumarin.eeproject.service;

import se.pumarin.eeproject.model.movie.Movie;
import se.pumarin.eeproject.model.movie.MovieEntity;
import se.pumarin.eeproject.model.movie.MovieList;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    public Optional<MovieEntity> deleteMovie(Long id);
    public Optional<MovieEntity> saveMovieForUser(int listNumber, Long userId, MovieList movieList);
    public MovieEntity saveMovie(MovieEntity movie);
    public MovieEntity convertToMovieEntity(Movie movie);
    public Movie convertToMovie(MovieEntity movieEntity);
    public Optional<MovieEntity> updateWatchedStatus(Long userId, Long movieId, boolean watched);
    public List<Movie> getAllMovies();
    public Optional<MovieEntity> updateBackdropPath(Long movieId, String newBackdropPath);
    Optional<MovieEntity> getMovieById(Long movieId);
}

