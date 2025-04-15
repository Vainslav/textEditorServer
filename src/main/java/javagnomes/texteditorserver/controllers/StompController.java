package javagnomes.texteditorserver.controllers;

import javagnomes.texteditorserver.dto.WSMessages.Function;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class StompController {
    @MessageMapping("/{path}")
    public Function handle(Function function) {
        return function;
    }
}
