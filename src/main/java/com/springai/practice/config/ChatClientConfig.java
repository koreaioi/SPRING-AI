package com.springai.practice.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class ChatClientConfig {

//    @Value(("classpath:/prompt.txt"))
//    private Resource resource; // txt를 사용했지만, 그냥 String Template 사용도 가능

    /*
    * ChatClient와 LLM 연결이 필요 이를 위해 API_KEY가 필요
    * application.yml에 설정해두면
    * chatClientBuilder 매개인자에 자동으로 주입됨.
    * */

    @Bean("simpleChatClient")
    public ChatClient simpleChatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder
                .defaultSystem("당신은 교육 튜터입니다. 개념을 명확하고 간단하게 설명하세요.")
                .build();
    }

    @Bean("advancedChatClient")
    public ChatClient advancedChatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder
                .build();
    }

    // 과거 대화 기억
    @Bean("advisorChatClient")
    public ChatClient advisorChatClient(ChatClient.Builder chatClientBuilder) {

        // 과거 대화 기록 기억을 위한 ChatMemory
        MessageWindowChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                .build();

        return chatClientBuilder
                .defaultAdvisors(MessageChatMemoryAdvisor
                        .builder(chatMemory)
                        .build())
                .build();
    }

}
