package com.springai.practice.starter;

import com.springai.practice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandLineAppStarterUpRunner implements CommandLineRunner {

    private final ChatService chatService;

    @Override
    public void run(String... args) throws Exception {
        chatService.startChat();
    }

}
