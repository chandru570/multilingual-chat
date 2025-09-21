package com.poc.spending_translate.utility;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

public class GenerateTextFromTextInput {
    public static String generateSummary(String prompt) {
        // The client gets the API key from the environment variable `GEMINI_API_KEY`.
        Client client = new Client();
        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-2.5-flash",
                        prompt + " Generate dummy data for illustration. Limit to 100 words.",
                        null);
        return response.text();
    }

//    public static void main(String[] args) {
//        String prompt = "Give a brief summary of my Credit Card spending habits over last three months. Generate dummy data for illustration.";
//        String summary = generateSummary(prompt);
//        System.out.println(summary);
//    }
}
