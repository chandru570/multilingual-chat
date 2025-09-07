package com.poc.spending_translate.config;

import com.poc.spending_translate.service.SpeechRecognitionService;
import com.poc.spending_translate.service.TranslationService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public TranslationService mockTranslationService() {
        return mock(TranslationService.class);
    }

    @Bean
    @Primary
    public SpeechRecognitionService mockSpeechRecognitionService() {
        return mock(SpeechRecognitionService.class);
    }
}
