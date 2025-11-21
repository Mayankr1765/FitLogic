package com.genai.Fitlogic.service;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FitService {

    private ChatModel model;

    public FitService(){
        this.model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("mistral")
                .temperature(0.5)
                .build();
    }

    public String getAnswer(String query) {
        String finalPrompt = """
            You are a certified fitness coach & nutrition specialist.
            Keep answers beginner-friendly and practical.

            User Question: %s
            """.formatted(query);
        try {
            String response = this.model.chat(finalPrompt);

            System.out.println("Response is " + response);

            return response;
        }
        catch (Exception e){
            System.out.println("exception is " + e);
            return new String("Exception" + e);
        }
    }







}
