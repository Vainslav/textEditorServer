package javagnomes.texteditorserver.ws.interceptors;

import javagnomes.texteditorserver.authorization.JwtUtil;
import javagnomes.texteditorserver.exseptions.JwtException;
import javagnomes.texteditorserver.exseptions.ws.CommandNotSupportedException;
import javagnomes.texteditorserver.exseptions.ws.DestinationNotSpecifiedException;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class WebSocketAuthInterceptor implements ChannelInterceptor {
    private final JwtUtil jwtUtil;
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketAuthInterceptor(JwtUtil jwtUtil, SimpMessagingTemplate messagingTemplate) {
        this.jwtUtil = jwtUtil;
        this.messagingTemplate = messagingTemplate;
    }

    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        try{
            if (accessor == null) {
                throw new RuntimeException("Could not get headers from Message");
            }
            handleIncomingMessage(accessor);
        } catch (Exception e) {
            SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor
                    .create(SimpMessageType.MESSAGE);
            headerAccessor.setSessionId(accessor.getSessionId());
            headerAccessor.setLeaveMutable(true);
            messagingTemplate.convertAndSendToUser(accessor.getSessionId(), "/queue/errors", e.getMessage(), headerAccessor.getMessageHeaders());
            return null;
        }

        return message;
    }

    public void handleIncomingMessage(StompHeaderAccessor accessor) {
        if (!StompCommand.SUBSCRIBE.equals(accessor.getCommand()) && !StompCommand.MESSAGE.equals(accessor.getCommand())) {
            return;
        }

        String destination = accessor.getFirstNativeHeader("destination");
        if (destination == null){
            throw new DestinationNotSpecifiedException("Destination not specified");
        }

        if (destination.equals("/user/queue/errors")){
            return;
        }

        if (!StompCommand.SEND.equals(accessor.getCommand()) && !StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            throw new CommandNotSupportedException("Command " + accessor.getCommand() + " not supported");
        }

        final String authorization = accessor.getFirstNativeHeader("Authorization");
        if (authorization == null){
            throw new JwtException("Authorization via jwt is required");
        }

        final String token = authorization.replace("Bearer ", "");

        if (!jwtUtil.verifyToken(token)){
            throw new JwtException("Could not verify token");
        }

        ArrayList<String> hostList = (ArrayList<String>) jwtUtil.getClaimFromToken(token, "host").asList(String.class);

        if (!hostList.contains(destination)) {
            throw new JwtException("Server not allowed");
        }
    }
}
