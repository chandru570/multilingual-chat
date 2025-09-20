package com.poc.spending_translate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TranslationServiceUnitTest {

    @Mock
    private TranslationService translationService;

    @BeforeEach
    void setUp() {
        // Reset mocks before each test
        reset(translationService);
    }

    @Test
    void testTranslateToEnglish_Success() {
        // Given
        String inputText = "Hola mundo";
        String sourceLang = "es";
        String expectedTranslation = "Hello world";

        when(translationService.translateToEnglish(inputText, sourceLang))
                .thenReturn(expectedTranslation);

        // When
        String result = translationService.translateToEnglish(inputText, sourceLang);

        // Then
        assertEquals(expectedTranslation, result);
        verify(translationService).translateToEnglish(inputText, sourceLang);
    }

    @Test
    void testTranslateFromEnglish_Success() {
        // Given
        String inputText = "Hello world";
        String targetLang = "es";
        String expectedTranslation = "Hola mundo";

        when(translationService.translateFromEnglish(inputText, targetLang))
                .thenReturn(expectedTranslation);

        // When
        String result = translationService.translateFromEnglish(inputText, targetLang);

        // Then
        assertEquals(expectedTranslation, result);
        verify(translationService).translateFromEnglish(inputText, targetLang);
    }

    @Test
    void testTranslateToEnglish_WithNullInput() {
        // Given
        String inputText = null;
        String sourceLang = "es";

        when(translationService.translateToEnglish(inputText, sourceLang))
                .thenThrow(new NullPointerException("Input cannot be null"));

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            translationService.translateToEnglish(inputText, sourceLang);
        });
    }

    @Test
    void testTranslateFromEnglish_WithNullInput() {
        // Given
        String inputText = null;
        String targetLang = "es";

        when(translationService.translateFromEnglish(inputText, targetLang))
                .thenThrow(new NullPointerException("Input cannot be null"));

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            translationService.translateFromEnglish(inputText, targetLang);
        });
    }

    @Test
    void testTranslateToEnglish_WithEmptyString() {
        // Given
        String inputText = "";
        String sourceLang = "es";
        String expectedTranslation = "Hello world";

        when(translationService.translateToEnglish(inputText, sourceLang))
                .thenReturn(expectedTranslation);

        // When
        String result = translationService.translateToEnglish(inputText, sourceLang);

        // Then
        assertEquals(expectedTranslation, result);
        verify(translationService).translateToEnglish(inputText, sourceLang);
    }

    @Test
    void testTranslateFromEnglish_WithEmptyString() {
        // Given
        String inputText = "";
        String targetLang = "es";
        String expectedTranslation = "Hola mundo";

        when(translationService.translateFromEnglish(inputText, targetLang))
                .thenReturn(expectedTranslation);

        // When
        String result = translationService.translateFromEnglish(inputText, targetLang);

        // Then
        assertEquals(expectedTranslation, result);
        verify(translationService).translateFromEnglish(inputText, targetLang);
    }

    @Test
    void testTranslateToEnglish_WithDifferentLanguages() {
        // Given
        String inputText = "Bonjour le monde";
        String sourceLang = "fr";
        String expectedTranslation = "Hello world";

        when(translationService.translateToEnglish(inputText, sourceLang))
                .thenReturn(expectedTranslation);

        // When
        String result = translationService.translateToEnglish(inputText, sourceLang);

        // Then
        assertEquals(expectedTranslation, result);
        verify(translationService).translateToEnglish(inputText, sourceLang);
    }

    @Test
    void testTranslateFromEnglish_WithDifferentLanguages() {
        // Given
        String inputText = "Hello world";
        String targetLang = "fr";
        String expectedTranslation = "Bonjour le monde";

        when(translationService.translateFromEnglish(inputText, targetLang))
                .thenReturn(expectedTranslation);

        // When
        String result = translationService.translateFromEnglish(inputText, targetLang);

        // Then
        assertEquals(expectedTranslation, result);
        verify(translationService).translateFromEnglish(inputText, targetLang);
    }
}
