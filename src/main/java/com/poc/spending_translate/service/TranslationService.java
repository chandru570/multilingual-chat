package com.poc.spending_translate.service;

import com.google.cloud.translate.Detection;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {

    private final Translate translate;

    public TranslationService() {
        this.translate = TranslateOptions.getDefaultInstance().getService();
    }

    public TranslateServiceResponse processTranslationQuery(String userInput, String userLang) {
        // Step 1: Detect language
        Detection detection = translate.detect(userInput);
        String detectedLang = detection.getLanguage();
        System.out.println("Language: " + detectedLang);
        System.out.println("Confidence: " + detection.getConfidence());

        // Step 2: Translate user input to English
        String translatedToEnglish = translateToEnglish(userInput, detectedLang);

    // Step 3: Send translated input to AI model (Gemini)
    String aiResponse = com.poc.spending_translate.utility.GenerateTextFromTextInput.generateSummary(translatedToEnglish);

        // Step 4: Translate AI response back to user's language
        String translatedResponseToNative = translateFromEnglish(aiResponse, detectedLang);

        // Step 5: Build response model
        return new TranslateServiceResponse(
            userLang,
            userInput,
            detectedLang,
            translatedToEnglish,
            aiResponse,
            translatedResponseToNative
        );
    }

    String translateToEnglish(String inputText, String sourceLang) {
        if(sourceLang.equals("en")) {
            return inputText; // No translation needed
        }
        Translation translation = translate.translate(
                inputText,
                Translate.TranslateOption.sourceLanguage(sourceLang),
                Translate.TranslateOption.targetLanguage("en")
        );
        return translation.getTranslatedText();
    }

    String translateFromEnglish(String inputText, String targetLang) {
        if(targetLang.equals("en")) {
            return inputText; // No translation needed
        }
        Translation translation = translate.translate(
                inputText,
                Translate.TranslateOption.sourceLanguage("en"),
                Translate.TranslateOption.targetLanguage(targetLang)
        );
        return translation.getTranslatedText();
    }

    private String mockAIResponse(String englishQuery) {
    // Deprecated: Use Gemini integration instead
    return "";
    }
}
