package se.pumarin.eeproject.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse implements Response {

        private String message;


        public ErrorResponse(String message) {
            this.message = message;
        }


}
