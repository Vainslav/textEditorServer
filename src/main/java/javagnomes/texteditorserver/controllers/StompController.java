package javagnomes.texteditorserver.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class StompController {
    @MessageMapping("/{path}")
    public String handle(){
        return getTimestamp();
    }

    private String getTimestamp() {
        return new SimpleDateFormat("MM/dd/yyyy h:mm:ss a").format(new Date());
    }
}
