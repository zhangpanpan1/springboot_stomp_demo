package com.demo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * Created by genghz on 18/4/11.
 */
@Component
public class WebSocketEventListener {

    private static final Logger log = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private WsUserRepository wsUserRepository;

    @EventListener
    public void handleConnectListener(SessionConnectedEvent event) {
        log.info("[ws-connected] socket connect: {}", event.getMessage());
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        wsUserRepository.addUser(accessor.getUser());
    }

    @EventListener
    public void handleDisconnectListener(SessionDisconnectEvent event) {
        log.info("[ws-disconnect] socket disconnect: {}", event.getMessage());
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        wsUserRepository.delUser(accessor.getUser());
    }
}
