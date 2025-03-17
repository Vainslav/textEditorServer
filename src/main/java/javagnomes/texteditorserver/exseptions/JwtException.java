package javagnomes.texteditorserver.exseptions;

public class JwtException extends RuntimeException {
    public JwtException(String message) {
        super(message);
    }
}
