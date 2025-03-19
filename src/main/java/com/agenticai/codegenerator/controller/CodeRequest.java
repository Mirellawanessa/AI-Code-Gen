package com.agenticai.codegenerator.controller;

public class CodeRequest {
    private String description;
    private String language;  // New field to define the programming language

    // Default constructor
    public CodeRequest() {}

    // Constructor with description and language
    public CodeRequest(String description, String language) {
        this.description = description;
        this.language = language;
    }

    // Getter and Setter for 'description'
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for 'language'
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
