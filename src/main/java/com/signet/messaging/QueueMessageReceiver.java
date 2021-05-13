package com.signet.messaging;

import org.springframework.stereotype.Component;

@Component
public class QueueMessageReceiver {
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
    }
}
