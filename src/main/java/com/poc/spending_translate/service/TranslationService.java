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

        // Step 2: Translate user input to English
        String translatedToEnglish = translateToEnglish(userInput, detectedLang);

        // Step 3: Send translated input to AI model (mocked here)
        String aiResponse = mockAIResponse(translatedToEnglish);

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
        Detection detection = translate.detect(inputText);
        System.out.println("Language: " + detection.getLanguage());
        System.out.println("Confidence: " + detection.getConfidence());

        Translation translation = translate.translate(
                inputText,
                Translate.TranslateOption.sourceLanguage(sourceLang),
                Translate.TranslateOption.targetLanguage("en")
        );
        return translation.getTranslatedText();
    }

    String translateFromEnglish(String inputText, String targetLang) {
        Translation translation = translate.translate(
                inputText,
                Translate.TranslateOption.sourceLanguage("en"),
                Translate.TranslateOption.targetLanguage(targetLang)
        );
        return translation.getTranslatedText();
    }

    private String mockAIResponse(String englishQuery) {
        // Replace with actual AI model integration
        return "Statement Overview\n" +
                "- Statement Date: 21 Aug 2025\n" +
                "- Due Date: 15 Sep 2025\n" +
                "- Balance from Last Statement: £35.84\n" +
                "- Balance on Statement Date: £89.67\n" +
                "- Minimum Payment Due: £5.00\n" +
                "- Payments Made: £199 total (two payments: £152 and £47)\n" +
                "- Total Transactions: 44 (covering late July to mid-August 2025)";
    }
}
