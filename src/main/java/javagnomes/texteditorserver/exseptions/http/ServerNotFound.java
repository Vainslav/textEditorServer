package javagnomes.texteditorserver.exseptions.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Server not found")
public class ServerNotFound extends RuntimeException {
    public ServerNotFound(String host) {
        super("Server with host " + host + " not found");
    }
}
