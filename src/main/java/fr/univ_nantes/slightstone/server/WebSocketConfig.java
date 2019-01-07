package fr.univ_nantes.slightstone.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/queue");
		config.setApplicationDestinationPrefixes("/app");
		//config.setUserDestinationPrefix("/user");
	}

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket")
        	.setAllowedOrigins("http://localhost:4200")
        	.setHandshakeHandler(new CustomHandshakeHandler())
        	.withSockJS();
        logger.warn("enregistrement endpoint !");
    }
}
