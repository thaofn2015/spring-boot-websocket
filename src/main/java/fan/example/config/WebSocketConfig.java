package fan.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// Thiet lap End point: var socket = new SockJS('/ws');
		registry.addEndpoint("/ws").withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// Prefix cho dich send message: 
		// stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
//		stompClient.send("/app/chat.addUser",{},
//			      JSON.stringify({sender: username, type: 'JOIN'})
//			  )
		registry.setApplicationDestinationPrefixes("/app");
		
		// Prefix cho dic nhan message: stompClient.subscribe('/topic/public', onMessageReceived);
		registry.enableSimpleBroker("/topic");
	}

	
}
