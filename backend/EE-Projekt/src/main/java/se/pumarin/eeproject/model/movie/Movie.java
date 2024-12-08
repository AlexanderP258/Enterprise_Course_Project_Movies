package se.pumarin.eeproject.model.movie;

import se.pumarin.eeproject.response.Response;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class Movie implements Response {

    @JsonProperty("title")
    private String title;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("adult")
    private boolean adult;

    @JsonProperty("backdrop_path")
    private String backdropPath;


    @JsonProperty("belongs_to_collection")
    @JsonIgnore
    private FilmCollection filmCollection;

    @JsonProperty("budget")
    private long budget;


    @JsonProperty("genres")
    @JsonIgnore
    private Genres genres;


    @JsonProperty("homepage")
    @JsonIgnore
    private String homepage;

    @JsonProperty("imdb_id")
    private String imdbId;

    @JsonProperty("origin_country")
    @JsonIgnore
    private OriginCountry originCountry;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("original_title")
    private String originalTitle;

    @JsonProperty("popularity")
    private double popularity;

    @JsonProperty("poster_path")
    private String posterPath;


    @JsonProperty("production_companies")
    @JsonIgnore
    private ProductionCompany productionCompanies;


    @JsonProperty("production_countries")
    @JsonIgnore
    private ProductionCountry productionCountries;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("revenue")
    private long revenue;

    @JsonProperty("runtime")
    private int runtime;

    @JsonProperty("spoken_languages")
    @JsonIgnore
    private SpokenLanguage spokenLanguage;

    @JsonProperty("status")
    private String status;

    @JsonProperty("tagline")
    private String tagline;

    @JsonProperty("video")
    @JsonIgnore
    private boolean video;

    @JsonProperty("vote_average")
    private double voteAverage;

    @JsonProperty("vote_count")
    private int voteCount;

}