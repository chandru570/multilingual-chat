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

    public String translateToEnglish(String inputText, String sourceLang) {
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

    public String translateFromEnglish(String inputText, String targetLang) {
        Translation translation = translate.translate(
                inputText,
                Translate.TranslateOption.sourceLanguage("en"),
                Translate.TranslateOption.targetLanguage(targetLang)
        );
        return translation.getTranslatedText();
    }
}
