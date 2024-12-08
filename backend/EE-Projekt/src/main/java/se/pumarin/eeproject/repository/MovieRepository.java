package se.pumarin.eeproject.repository;


import se.pumarin.eeproject.model.movie.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
}
