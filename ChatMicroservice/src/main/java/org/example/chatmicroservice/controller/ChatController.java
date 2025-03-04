package org.example.chatmicroservice.controller;

import org.example.chatmicroservice.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/sendMessage")
    public void sendMessage(Message message) {
        logger.info("Received message: Sender ID = {}, Recipient ID = {}, Content = {}",
                message.getSenderId(), message.getRecipientId(), message.getContent());

        // send the message to the common topic "/topic/messages"
        messagingTemplate.convertAndSend("/topic/messages", message);
    }

}
