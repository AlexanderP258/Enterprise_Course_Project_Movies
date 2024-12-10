package se.pumarin.eeproject.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse implements Response {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}
