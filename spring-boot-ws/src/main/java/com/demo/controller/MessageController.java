package com.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;

/**
 * Created by genghz on 18/4/11.
 */
@Controller
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @MessageMapping("/test/{id}")
    public void test(Message message,
                     MessageHeaders MessageHeaders,
                     @Header("destination") String destination,
                     @Headers Map<String, Object> headers,
                     @DestinationVariable long id,
                     @Payload String body) {
        log.info("[test] Message: {}", message);
        log.info("[test] MessageHeaders: {}", MessageHeaders);
        log.info("[test] Header: {}", destination);
        log.info("[test] Headers: {}", headers);
        log.info("[test] DestinationVariable: {}", id);
        log.info("[test] Payload: {}", body);
    }

    // ---------------------- 广播推送 ----------------------
    @MessageMapping("/hello")
    public void hello(@Payload String body) {
        print(body);
        simpMessagingTemplate.convertAndSend("/sub/public", "reply hello");
    }

    @MessageMapping("/hello1")
    @SendTo("/sub/public")
    public String hello1(@Payload String body) {
        print(body);
        return "reply hello1";
    }

    @MessageMapping("/hello2")
    public void hello2(@Payload String body) {
        print(body);
        simpMessagingTemplate.convertAndSend("/aa/msg", "reply hello2");
    }

    // ---------------------- 对点推送 ----------------------
    @MessageMapping("/hello3")
    public void hello3(@Payload String body, Principal principal) {
        print(body);
        print(principal);
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/topic/sub/msg", "reply hello3");
    }

    @MessageMapping("/hello4")
    @SendToUser("/sub/msg")
    public String hello4(@Payload String body) {
         print(body);
        return "reply hello4";
    }

    private void print(Object data) {
        log.info("receive message body: {}", data);
    }
}
