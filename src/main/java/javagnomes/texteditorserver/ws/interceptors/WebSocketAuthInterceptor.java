package javagnomes.texteditorserver.ws.interceptors;

import javagnomes.texteditorserver.authorization.JwtUtil;
import javagnomes.texteditorserver.exseptions.JwtException;
import javagnomes.texteditorserver.exseptions.ws.CommandNotSupportedException;
import javagnomes.texteditorserver.exseptions.ws.DestinationNotSpecifiedException;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class WebSocketAuthInterceptor implements ChannelInterceptor {
    private JwtUtil jwtUtil;
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketAuthInterceptor(JwtUtil jwtUtil, SimpMessagingTemplate messagingTemplate) {
        this.jwtUtil = jwtUtil;
        this.messagingTemplate = messagingTemplate;
    }

    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        System.out.println(message);
//        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//        if (accessor == null) {
//            return null;
//        }
//
//        if (StompCommand.CONNECT.equals(accessor.getCommand()) || StompCommand.DISCONNECT.equals(accessor.getCommand())) {
//            return message;
//        }
//
//        try {
//            if (!StompCommand.SEND.equals(accessor.getCommand()) && !StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
//                throw new CommandNotSupportedException("Command " + accessor.getCommand() + " not supported");
//            }
//
//            final String authorization = accessor.getFirstNativeHeader("Authorization");
//            if (authorization == null){
//                throw new JwtException("Authorization via jwt is required");
//            }
//
//            if (!jwtUtil.verifyToken(authorization)){
//                throw new JwtException("Could not verify token");
//            }
//
//            ArrayList<String> hostList = (ArrayList<String>) jwtUtil.getClaimFromToken(authorization, "id").asList(String.class);
//
//            String destination = accessor.getFirstNativeHeader("destination");
//            if (destination == null){
//                throw new DestinationNotSpecifiedException("Destination not specified");
//            }
//
//            if (!hostList.contains(destination)) {
//                throw new JwtException("Server not allowed");
//            }
//        }catch (RuntimeException e){
//            String sessionId = accessor.getSessionId();
//            messagingTemplate.convertAndSendToUser(sessionId, "/errors", e.getMessage());
//            return null;
//        }
//

        return message;
    }
}
