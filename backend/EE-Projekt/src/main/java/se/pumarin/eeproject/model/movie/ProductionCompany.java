package se.pumarin.eeproject.model.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductionCompany {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("logo path")
    private String logoPath;

    @JsonProperty("name")
    private String name;

    @JsonProperty("origin country")
    private String originCountry;

}