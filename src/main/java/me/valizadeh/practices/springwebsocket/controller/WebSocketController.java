package me.valizadeh.practices.springwebsocket.controller;

import com.google.gson.Gson;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;

@Controller
public class WebSocketController {

	/**
	 * Sending message based on client request
	 * @param message
	 * @param principal
	 * @return
	 */
	@MessageMapping("/messageStomp")
	@SendToUser("/topic/reply")
	public String processMessageFromClient(@Payload String message, Principal principal) {
		String name = new Gson().fromJson(message, Map.class).get("name").toString();
//		messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/reply", name);
		return name;
	}
	
	@MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }
}