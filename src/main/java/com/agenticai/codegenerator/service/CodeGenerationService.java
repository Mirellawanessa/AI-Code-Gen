package com.agenticai.codegenerator.service;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Service
public class CodeGenerationService {

    private static final String API_KEY = "YOUR_API-KEY";  // Replace with your actual key
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/completions";

    public String generateCode(String description, String language) {
        OkHttpClient client = new OkHttpClient();

        // Generate the prompt based on the language
        String prompt = generatePrompt(description, language);

        // Create the JSON request for the API
        String json = "{"
                + "\"model\": \"text-davinci-003\","
                + "\"prompt\": \"" + prompt + "\","
                + "\"max_tokens\": 150"  // Limit response to 150 tokens
                + "}";

        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(OPENAI_API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    String responseBodyString = responseBody.string();
                    return extractGeneratedCode(responseBodyString);
                } else {
                    return "Error generating code: response body is null";
                }
            } else {
                return "Error generating code: " + response.message();
            }
        } catch (IOException e) {
            return "Error connecting to the API: " + e.getMessage();
        } catch (JSONException e) {
            return "Error processing the API response: " + e.getMessage();
        }
    }

    // Method to create the prompt based on the selected language
    private String generatePrompt(String description, String language) {
        if (language == null || language.isEmpty()) {
            language = "Java";  // Default to Java if not specified
        }
        return "Create a " + language + " code for the following description: " + description;
    }

    // Method to extract the generated code from the JSON response
    private String extractGeneratedCode(String responseBody) {
        try {
            JSONObject jsonResponse = new JSONObject(responseBody);
            return jsonResponse.getJSONArray("choices")
                               .getJSONObject(0)
                               .getString("text")
                               .trim();
        } catch (JSONException | NullPointerException e) {
            return "Error processing the API response: " + e.getMessage();
        }
    }
}
