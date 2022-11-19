package fan.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import fan.example.model.ChatMessage;

@Component
public class WebSocketEventListener {
			
	@Autowired
	private SimpMessageSendingOperations messageingTemplae;
	 
	@EventListener
	public void handleWenSocketConnectListener(SessionConnectedEvent event) {
		System.out.println("Received a new web socket connection");
	}
	
	@EventListener
	public void handleWebSocketDisconnectLister(SessionDisconnectEvent event) {
		
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		
		String username = (String) headerAccessor.getSessionAttributes().get(("username"));
		
		if (username != null) {
			System.out.println("User disconnect: " + username);
			
			ChatMessage chatMessage = new ChatMessage();
			chatMessage.setType(ChatMessage.MessageType.LEAVE);
			chatMessage.setSender(username);
			
			messageingTemplae.convertAndSend("/topic/public", chatMessage);
		}
	}
}
