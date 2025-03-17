package javagnomes.texteditorserver.exseptions.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Incorrect password")
public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException() {
        super("Password incorrect");
    }
}
