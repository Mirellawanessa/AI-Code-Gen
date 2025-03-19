package com.agenticai.codegenerator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agenticai.codegenerator.service.CodeGenerationService;

// The controller handles API requests
@RestController
@RequestMapping("/api")
public class CodeGenerationController {

    @Autowired
    private CodeGenerationService codeGenerationService;

    // Endpoint to generate code based on description and language
    @PostMapping("/generate")
    public String generateCode(@RequestBody CodeRequest codeRequest) {
        // Calling the service to generate the code
        return codeGenerationService.generateCode(codeRequest.getDescription(), codeRequest.getLanguage());
    }
}
