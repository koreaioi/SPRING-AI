package com.springai.practice.controller;

import com.springai.practice.service.ChatService;
import io.swagger.v3.oas.annotations.media.DependentRequired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AskAIController {

    private final ChatService chatService;
    public AskAIController(ChatService chatService) {
        this.chatService = chatService;
    }

    // 기본 chatModel를 사용해 OpenAi와 통신
    @GetMapping("/ask")
    public  String getResponse(@RequestParam("message") String message){
        return chatService.getResponse(message);
    }

    // 조절된 ChatOption을 사용하여 OpenAi와 통신
    @GetMapping("/ask-ai")
    public  String getResponseOptions(@RequestParam("message") String message){
        return chatService.getResponseOptions(message);
    }

}
