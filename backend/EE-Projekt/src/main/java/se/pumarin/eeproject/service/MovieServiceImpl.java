package se.pumarin.eeproject.service;

import se.pumarin.eeproject.model.User;
import se.pumarin.eeproject.model.movie.Movie;
import se.pumarin.eeproject.model.movie.MovieEntity;
import se.pumarin.eeproject.model.movie.MovieList;
import se.pumarin.eeproject.repository.MovieRepository;
import se.pumarin.eeproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, UserRepository userRepository, UserService userService) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Transactional
    public Optional<MovieEntity> deleteMovie(Long id) {
        Optional<MovieEntity> movieEntity = movieRepository.findById(id);

        if (movieEntity.isPresent()) {
            MovieEntity movie = movieEntity.get();

            if (movie.getUser() != null) {
                User user = movie.getUser();
                user.getWatchlist().remove(movie);
            }

            movieRepository.delete(movie);

            return Optional.of(movie);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<MovieEntity> saveMovieForUser(int listNumber, Long userId, MovieList movieList) {
        if (movieList == null || movieList.getResults() == null || movieList.getResults().isEmpty()) {
            return Optional.empty();
        }

        if (listNumber < 0 || listNumber >= movieList.getResults().size()) {
            return Optional.empty();
        }

        Movie movie = movieList.getResults().get(listNumber);

        MovieEntity movieEntity = convertToMovieEntity(movie);

        Optional<User> userOptional = userService.getUserById(userId);
        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        User user = userOptional.get();

        if (user.getWatchlist().stream().anyMatch(m -> m.getTitle().equals(movieEntity.getTitle()))) {
            return Optional.empty();
        }

        movieEntity.setUser(user);

        return Optional.of(saveMovie(movieEntity));
    }

    @Override
    public MovieEntity saveMovie(MovieEntity movie) {
        return movieRepository.save(movie);
    }

    @Override
    public MovieEntity convertToMovieEntity(Movie movie) {
        MovieEntity movieEntity = new MovieEntity();

        movieEntity.setTitle(movie.getTitle());
        movieEntity.setAdult(movie.isAdult());
        movieEntity.setBackdropPath(movie.getBackdropPath());
        movieEntity.setBudget(movie.getBudget());
        movieEntity.setImdbId(movie.getImdbId());
        movieEntity.setOverview(movie.getOverview());
        movieEntity.setOriginalLanguage(movie.getOriginalLanguage());
        movieEntity.setOriginalTitle(movie.getOriginalTitle());
        movieEntity.setPopularity(movie.getPopularity());
        movieEntity.setPosterPath(movie.getPosterPath());
        movieEntity.setReleaseDate(movie.getReleaseDate());
        movieEntity.setRevenue(movie.getRevenue());
        movieEntity.setRuntime(movie.getRuntime());
        movieEntity.setStatus(movie.getStatus());
        movieEntity.setTagline(movie.getTagline());
        movieEntity.setVoteAverage(movie.getVoteAverage());
        movieEntity.setVoteCount(movie.getVoteCount());
        return movieEntity;
    }

    // Converts MovieEntity to MovieObject
    @Override
    public Movie convertToMovie(MovieEntity movieEntity) {
        Movie movie = new Movie();
        String baseImageUrl = "https://image.tmdb.org/t/p/original";

        movie.setTitle(movieEntity.getTitle());
        movie.setAdult(movieEntity.isAdult());
        movie.setBackdropPath(baseImageUrl + movieEntity.getBackdropPath());
        movie.setBudget(movieEntity.getBudget());
        movie.setImdbId(movieEntity.getImdbId());
        movie.setOverview(movieEntity.getOverview());
        movie.setOriginalLanguage(movieEntity.getOriginalLanguage());
        movie.setOriginalTitle(movieEntity.getOriginalTitle());
        movie.setPopularity(movieEntity.getPopularity());
        movie.setPosterPath(baseImageUrl + movieEntity.getPosterPath());
        movie.setReleaseDate(movieEntity.getReleaseDate());
        movie.setRevenue(movieEntity.getRevenue());
        movie.setRuntime(movieEntity.getRuntime());
        movie.setStatus(movieEntity.getStatus());
        movie.setTagline(movieEntity.getTagline());
        movie.setVoteAverage(movieEntity.getVoteAverage());
        movie.setVoteCount(movieEntity.getVoteCount());
        return movie;
    }


    public Optional<MovieEntity> updateWatchedStatus(Long userId, Long movieId, boolean watched) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<MovieEntity> watchlist = user.getWatchlist();

            for (MovieEntity movie : watchlist) {
                if (movie.getMovieId().equals(movieId)) {
                    movie.setWatched(watched);

                    saveMovie(movie);

                    return Optional.of(movie);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Movie> getAllMovies() {
        List<MovieEntity> movieEntities = movieRepository.findAll();

        if (movieEntities == null || movieEntities.isEmpty()) {
            return List.of();
        }

        return movieEntities.stream()
                .map(this::convertToMovie)
                .toList();
    }

    @Override
    public Optional<MovieEntity> updateBackdropPath(Long movieId, String newBackdropPath) {
        Optional<MovieEntity> movieOptional = movieRepository.findById(movieId);

        if (movieOptional.isPresent()) {
            MovieEntity movie = movieOptional.get();
            movie.setBackdropPath(newBackdropPath);
            movieRepository.save(movie);
            return Optional.of(movie);
        }

        return Optional.empty();
    }

    @Override
    public Optional<MovieEntity> getMovieById(Long movieId) {
        return movieRepository.findById(movieId);
    }

}
