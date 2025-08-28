package com.springai.practice.service;

import com.springai.practice.vo.Movie;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class ChatService {

    private final ChatClient simpleChatClient;
    private final ChatClient advancedChatClient;
    private final ChatClient advisorChatClient;

    public ChatService(
            @Qualifier("simpleChatClient") ChatClient simpleChatClient,
            @Qualifier("advancedChatClient") ChatClient advancedChatClient,
            @Qualifier("advisorChatClient") ChatClient advisorChatClient
    ) {
        this.simpleChatClient = simpleChatClient;
        this.advancedChatClient = advancedChatClient;
        this.advisorChatClient = advisorChatClient;
    }
    /*
    * user -> 사용자 메세지
    * call -> 호출
    * content -> 요청 정보를 받는 부분.
    * */

    public String chat(String message) {
        return simpleChatClient.prompt()
                .user(message)
                .call()
                .content();
    }

    public ChatResponse chatMessage(String message) {
        return simpleChatClient.prompt()
                .user(message)
                .call()
                .chatResponse();
    }

    public String chatPlaceHolder(String subject, String tone, String message) {
        return simpleChatClient.prompt()
                .user(message)
                .system(s -> s
                        .param("subject", subject)
                        .param("tone", tone)
                )
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getText();
    }


    // List<String>으로 응답하기
    public List<String> chatList(String message) {
        return simpleChatClient.prompt()
                .user(message)
                .call()
                .entity(new ListOutputConverter(new DefaultConversionService()));
//                .entity(new MapOutputConverter(new DefaultConversionService())); // Map<String, String>으로 반환하는 경우
    }

    public List<Movie> chatMovie(String directorName) {

        String template = """
                "Generate a list of movies directed by {directorName}. If the director is unknown, return null.
                한국 영화는 한글로 표기해줘.
                Each movie should include a title and release year. {format}"
                """;

        List<Movie> result = simpleChatClient.prompt()
                .user(spec -> spec.text(template)
                        .param("directorName", directorName)
                        .param("format", "json")
                )
                .call()
                .entity(new ParameterizedTypeReference<List<Movie>>() {

                });

        return result;
    }

    public void startChat() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your message:");

        while (true) {
            String message = sc.nextLine();

            if(message.equals("exit")) {
                System.out.println("Exiting Chat....");
                break;
            }

            String response = getResponse(message);
            System.out.println("Bot: " + response);

        }

        sc.close();
    }

    public String getResponse(String message) {
        return advisorChatClient.prompt()
                .user(message)
                .call()
                .content();
    }

}
