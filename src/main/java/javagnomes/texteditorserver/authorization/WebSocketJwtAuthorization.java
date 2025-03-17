package javagnomes.texteditorserver.authorization;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.messaging.access.intercept.MessageAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;

@Component
public class WebSocketJwtAuthorization implements AuthorizationManager<MessageAuthorizationContext<?>> {
    private final JwtUtil jwtUtil;

    public WebSocketJwtAuthorization(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void verify(Supplier<Authentication> authentication, MessageAuthorizationContext<?> object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, MessageAuthorizationContext<?> object) {
        return null;
    }

    @Override
    public AuthorizationResult authorize(Supplier<Authentication> authentication, MessageAuthorizationContext<?> object) {
        System.out.println(object.getMessage());
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(object.getMessage());
        List<String> authHeaders = headers.getNativeHeader("Authorization");

        if (authHeaders == null || authHeaders.isEmpty()) {
            return new AuthorizationDecision(false);
        }

        final String destination = headers.getDestination();

        if (destination == null) {
            return new AuthorizationDecision(false);
        }

        String token = authHeaders.get(0).replace("Bearer ", "");

        boolean isValid = jwtUtil.getClaimFromToken(token, "host").asString().equals(destination);
        if (isValid) {
            return new AuthorizationDecision(true);
        } else {
            return new AuthorizationDecision(false);
        }
    }
}
