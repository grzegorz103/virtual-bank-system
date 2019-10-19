package com.ii.app.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class ApiResponse {
    private String message;
    private List<String> messages;

    public ApiResponse(String message) {
        this.message = message;
    }

    public ApiResponse(List<String> messages) {
        this.messages = messages;
    }
}
