package com.springai.practice.controller;

import com.springai.practice.service.ChatService;
import com.springai.practice.vo.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/chat")
    public String chat(@RequestParam(name = "message") String message) {

        return chatService.chat(message);
    }

    @GetMapping("/chat/system")
    public String chatSystem(@RequestParam(name = "message") String message) {
        ChatResponse response = chatService.chatMessage(message);

        return response.getResult().getOutput().getText();
    }

    @GetMapping("/chat/placeholder")
    public String chatPlaceholder(
            @RequestParam(name = "subject") String subject,
            @RequestParam(name = "tone") String tone,
            @RequestParam(name = "message") String message
    ) {

        return chatService.chatPlaceHolder(subject, tone, message);
    }

    @GetMapping("/chat/list")
    public List<String> chatList(@RequestParam(name = "message") String message) {
        return chatService.chatList(message);
    }

    @GetMapping("/chat/movie")
    public List<Movie> chatMovie(
            @RequestParam(name = "directorName") String directorName
    ) {
        List<Movie> response = chatService.chatMovie(directorName);

        return response;
    }

}
