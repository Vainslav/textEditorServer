package javagnomes.texteditorserver.configurations;

import javagnomes.texteditorserver.authorization.WebSocketJwtAuthorization;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;

@Configuration
@EnableWebSocketSecurity
public class WebSocketSecurity {
    private final WebSocketJwtAuthorization webSocketJwtAuthorization;

    public WebSocketSecurity(WebSocketJwtAuthorization webSocketJwtAuthorization) {
        this.webSocketJwtAuthorization = webSocketJwtAuthorization;
    }

    @Bean
    public AuthorizationManager<Message<?>> messageAuthorizationManager() {
        MessageMatcherDelegatingAuthorizationManager.Builder messages =
                MessageMatcherDelegatingAuthorizationManager.builder();
        messages
                .simpSubscribeDestMatchers("/topic/**").permitAll()
                .simpDestMatchers("/app/**").permitAll()
                .anyMessage().denyAll();

        return messages.build();
    }
}
