package se.pumarin.eeproject.model.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OriginCountry {

    @JsonProperty("code")
    private String countryCode;

}