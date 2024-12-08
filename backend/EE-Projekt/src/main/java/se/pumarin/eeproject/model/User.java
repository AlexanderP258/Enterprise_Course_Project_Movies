package se.pumarin.eeproject.model;

import se.pumarin.eeproject.model.movie.MovieEntity;
import se.pumarin.eeproject.response.Response;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name="users")
@Getter
@Setter
public class User implements Response {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String username;

    private String password;


    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<MovieEntity> watchlist;

}
