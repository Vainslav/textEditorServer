package javagnomes.texteditorserver.controllers;

import javagnomes.texteditorserver.authorization.JwtUtil;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class StompController {
    private final JwtUtil jwtUtil;

    public StompController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @MessageMapping("/{path}")
    @PreAuthorize("jwtUtil.getClaimFromToken(accessor.getFirstNativeHeader(\"Authorization\"), \"id\").asString() == path")
    public String handle(SimpMessageHeaderAccessor accessor, @PathVariable String path) throws Exception {
        System.out.println(123);
        return getTimestamp();
    }

    private String getTimestamp() {
        return new SimpleDateFormat("MM/dd/yyyy h:mm:ss a").format(new Date());
    }
}
