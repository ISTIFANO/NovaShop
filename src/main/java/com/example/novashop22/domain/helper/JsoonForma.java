package com.example.novashop22.domain.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsoonForma {

    private final ObjectMapper objectMapper;

    public JsoonForma() {
        this.objectMapper = new ObjectMapper();
    }

    public void printJson(Object messageRequest) {
        try {
            String requestJson = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(messageRequest);
            System.out.println("Request Body (JSON):\n" + requestJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
