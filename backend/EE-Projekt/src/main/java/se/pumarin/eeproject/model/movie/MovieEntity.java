package se.pumarin.eeproject.model.movie;

import se.pumarin.eeproject.model.User;
import se.pumarin.eeproject.response.Response;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table( name = "movies")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieEntity implements Response {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long movieId;

    //private Long userId;

    private String title;

    @Column(length = 5000)
    private String overview;

    private boolean watched;

    private boolean adult;

    private String backdropPath;

    private long budget;

    private String imdbId;

    private String originalLanguage;

    private String originalTitle;

    private double popularity;

    private String posterPath;

    private String releaseDate;

    private long revenue;

    private int runtime;

    private String status;
    private String tagline;

    private double voteAverage;

    private int voteCount;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

}
