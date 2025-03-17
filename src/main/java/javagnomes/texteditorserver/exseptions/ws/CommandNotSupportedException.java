package javagnomes.texteditorserver.exseptions.ws;

public class CommandNotSupportedException extends WsException {
    public CommandNotSupportedException(String message) {
        super(message);
    }
}
