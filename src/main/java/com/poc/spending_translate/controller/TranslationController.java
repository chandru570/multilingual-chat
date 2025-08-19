package com.poc.spending_translate.controller;

import com.poc.spending_translate.service.TranslationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/translate")
public class TranslationController {

    private final TranslationService translationService;

    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @GetMapping("/query")
    public String handleQuery(@RequestParam String userInput,
                              @RequestParam String userLang) {

        // Step 1: Translate user input to English
        String translatedToEnglish = translationService.translateToEnglish(userInput, userLang);
        System.out.println("Original input: " + userInput);
        System.out.println("Translated to English: " + translatedToEnglish);

        // Step 2: Send translated input to AI model (mocked here)
        String aiResponse = mockAIResponse(translatedToEnglish);

        // Step 3: Translate AI response back to user's language
        return translationService.translateFromEnglish(aiResponse, userLang);
    }

    private String mockAIResponse(String englishQuery) {
        // Replace with actual AI model integration
        return "Your last transaction was $250 at Tesco.";
    }
}
