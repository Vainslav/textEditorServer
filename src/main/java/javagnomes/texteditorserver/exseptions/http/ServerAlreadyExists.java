package javagnomes.texteditorserver.exseptions.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Server already exists")
public class ServerAlreadyExists extends RuntimeException {
    public ServerAlreadyExists(String host) {
        super("Server with host " + host + " already exists");
    }
}
