package com.poc.spending_translate.controller;

import com.poc.spending_translate.service.TranslationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TranslationControllerTest {

    @Mock
    private TranslationService translationService;

    private TranslationController translationController;

    @BeforeEach
    void setUp() {
        translationController = new TranslationController(translationService);
    }

    @Test
    void testHandleQuery_Success() {
        // Given
        String userInput = "Hola mundo";
        String userLang = "es";
        String translatedToEnglish = "Hello world";
        String expectedResponse = "Statement Overview\n" +
                "- Statement Date: 21 Aug 2025\n" +
                "- Due Date: 15 Sep 2025\n" +
                "- Balance from Last Statement: £35.84\n" +
                "- Balance on Statement Date: £89.67\n" +
                "- Minimum Payment Due: £5.00\n" +
                "- Payments Made: £199 total (two payments: £152 and £47)\n" +
                "- Total Transactions: 44 (covering late July to mid-August 2025)";

        when(translationService.translateToEnglish(userInput, userLang)).thenReturn(translatedToEnglish);
        when(translationService.translateFromEnglish(anyString(), eq(userLang))).thenReturn(expectedResponse);

        // When
        String result = translationController.handleQuery(userInput, userLang);

        // Then
        assertEquals(expectedResponse, result);
        verify(translationService).translateToEnglish(userInput, userLang);
        verify(translationService).translateFromEnglish(anyString(), eq(userLang));
    }

    @Test
    void testHandleQuery_WithEmptyInput() {
        // Given
        String userInput = "";
        String userLang = "es";
        String translatedToEnglish = "";
        String expectedResponse = "Statement Overview\n" +
                "- Statement Date: 21 Aug 2025\n" +
                "- Due Date: 15 Sep 2025\n" +
                "- Balance from Last Statement: £35.84\n" +
                "- Balance on Statement Date: £89.67\n" +
                "- Minimum Payment Due: £5.00\n" +
                "- Payments Made: £199 total (two payments: £152 and £47)\n" +
                "- Total Transactions: 44 (covering late July to mid-August 2025)";

        when(translationService.translateToEnglish(userInput, userLang)).thenReturn(translatedToEnglish);
        when(translationService.translateFromEnglish(anyString(), eq(userLang))).thenReturn(expectedResponse);

        // When
        String result = translationController.handleQuery(userInput, userLang);

        // Then
        assertEquals(expectedResponse, result);
        verify(translationService).translateToEnglish(userInput, userLang);
        verify(translationService).translateFromEnglish(anyString(), eq(userLang));
    }

    @Test
    void testHandleQuery_WithNullInput() {
        // Given
        String userInput = null;
        String userLang = "es";

        // When the service is called with null input, it should handle it gracefully
        // or throw an exception depending on implementation
        when(translationService.translateToEnglish(userInput, userLang))
                .thenThrow(new IllegalArgumentException("Input cannot be null"));

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            translationController.handleQuery(userInput, userLang);
        });
    }

    @Test
    void testHandleQuery_WithNullLanguage() {
        // Given
        String userInput = "Hello";
        String userLang = null;

        // When the service is called with null language, it should handle it gracefully
        // or throw an exception depending on implementation
        when(translationService.translateToEnglish(userInput, userLang))
                .thenThrow(new IllegalArgumentException("Language cannot be null"));

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            translationController.handleQuery(userInput, userLang);
        });
    }

    @Test
    void testHandleQuery_WithDifferentLanguages() {
        // Given
        String userInput = "Bonjour le monde";
        String userLang = "fr";
        String translatedToEnglish = "Hello world";
        String expectedResponse = "Statement Overview\n" +
                "- Statement Date: 21 Aug 2025\n" +
                "- Due Date: 15 Sep 2025\n" +
                "- Balance from Last Statement: £35.84\n" +
                "- Balance on Statement Date: £89.67\n" +
                "- Minimum Payment Due: £5.00\n" +
                "- Payments Made: £199 total (two payments: £152 and £47)\n" +
                "- Total Transactions: 44 (covering late July to mid-August 2025)";

        when(translationService.translateToEnglish(userInput, userLang)).thenReturn(translatedToEnglish);
        when(translationService.translateFromEnglish(anyString(), eq(userLang))).thenReturn(expectedResponse);

        // When
        String result = translationController.handleQuery(userInput, userLang);

        // Then
        assertEquals(expectedResponse, result);
        verify(translationService).translateToEnglish(userInput, userLang);
        verify(translationService).translateFromEnglish(anyString(), eq(userLang));
    }

    @Test
    void testHandleQuery_TranslationServiceThrowsException() {
        // Given
        String userInput = "Hola mundo";
        String userLang = "es";

        when(translationService.translateToEnglish(userInput, userLang))
                .thenThrow(new RuntimeException("Translation service error"));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            translationController.handleQuery(userInput, userLang);
        });

        verify(translationService).translateToEnglish(userInput, userLang);
        verify(translationService, never()).translateFromEnglish(anyString(), anyString());
    }

    @Test
    void testHandleQuery_VerifyMockAIResponse() {
        // Given
        String userInput = "Test input";
        String userLang = "en";
        String translatedToEnglish = "Test input";
        String expectedResponse = "Statement Overview\n" +
                "- Statement Date: 21 Aug 2025\n" +
                "- Due Date: 15 Sep 2025\n" +
                "- Balance from Last Statement: £35.84\n" +
                "- Balance on Statement Date: £89.67\n" +
                "- Minimum Payment Due: £5.00\n" +
                "- Payments Made: £199 total (two payments: £152 and £47)\n" +
                "- Total Transactions: 44 (covering late July to mid-August 2025)";

        when(translationService.translateToEnglish(userInput, userLang)).thenReturn(translatedToEnglish);
        when(translationService.translateFromEnglish(anyString(), eq(userLang))).thenReturn(expectedResponse);

        // When
        String result = translationController.handleQuery(userInput, userLang);

        // Then
        assertEquals(expectedResponse, result);
        assertTrue(result.contains("Statement Overview"));
        assertTrue(result.contains("Statement Date"));
        assertTrue(result.contains("Due Date"));
        assertTrue(result.contains("Balance from Last Statement"));
        assertTrue(result.contains("Balance on Statement Date"));
        assertTrue(result.contains("Minimum Payment Due"));
        assertTrue(result.contains("Payments Made"));
        assertTrue(result.contains("Total Transactions"));
    }
}
