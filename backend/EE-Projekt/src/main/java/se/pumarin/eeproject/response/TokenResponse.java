package se.pumarin.eeproject.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse implements Response {
    private String token;

    public TokenResponse(String token) {
        this.token = token;
    }
}
