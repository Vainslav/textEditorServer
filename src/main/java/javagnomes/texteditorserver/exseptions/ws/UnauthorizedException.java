package javagnomes.texteditorserver.exseptions.ws;

public class UnauthorizedException extends WsException {
    public UnauthorizedException() {
        super("Unauthorized");
    }
}
