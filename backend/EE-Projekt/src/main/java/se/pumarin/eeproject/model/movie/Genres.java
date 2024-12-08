package se.pumarin.eeproject.model.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Genres {


    @JsonProperty("id")
    private Long id;

    @JsonProperty("names")
    private List<String> names;



}