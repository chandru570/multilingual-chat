package com.poc.spending_translate.controller;

import com.poc.spending_translate.service.TranslationService;
import com.poc.spending_translate.service.TranslateServiceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
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
    TranslateServiceResponse mockResponse = new TranslateServiceResponse(
        userLang,
        userInput,
        "es",
        "Hello world",
        "Statement Overview\n- Statement Date: 21 Aug 2025\n- Due Date: 15 Sep 2025\n- Balance from Last Statement: £35.84\n- Balance on Statement Date: £89.67\n- Minimum Payment Due: £5.00\n- Payments Made: £199 total (two payments: £152 and £47)\n- Total Transactions: 44 (covering late July to mid-August 2025)",
        "Statement Overview\n- Statement Date: 21 Aug 2025\n- Due Date: 15 Sep 2025\n- Balance from Last Statement: £35.84\n- Balance on Statement Date: £89.67\n- Minimum Payment Due: £5.00\n- Payments Made: £199 total (two payments: £152 and £47)\n- Total Transactions: 44 (covering late July to mid-August 2025)"
    );
    when(translationService.processTranslationQuery(userInput, userLang)).thenReturn(mockResponse);

    // When
    TranslateServiceResponse response = translationController.handleQuery(userInput, userLang);

    // Then
    assertEquals(mockResponse.getUserInputLanguage(), response.getUserInputLanguage());
    assertEquals(mockResponse.getUserInputTextNative(), response.getUserInputTextNative());
    assertEquals(mockResponse.getModelTranslateToEnglish(), response.getModelTranslateToEnglish());
    assertEquals(mockResponse.getResponseDataInEnglish(), response.getResponseDataInEnglish());
    assertEquals(mockResponse.getModelTranslateResponseToNative(), response.getModelTranslateResponseToNative());
    }

    @Test
    void testHandleQuery_WithEmptyInput() {
        // Given
        String userInput = "";
        String userLang = "es";
    TranslateServiceResponse mockResponse = new TranslateServiceResponse(
        userLang,
        userInput,
        "es",
        "",
        "Statement Overview\n- Statement Date: 21 Aug 2025\n- Due Date: 15 Sep 2025\n- Balance from Last Statement: £35.84\n- Balance on Statement Date: £89.67\n- Minimum Payment Due: £5.00\n- Payments Made: £199 total (two payments: £152 and £47)\n- Total Transactions: 44 (covering late July to mid-August 2025)",
        "Statement Overview\n- Statement Date: 21 Aug 2025\n- Due Date: 15 Sep 2025\n- Balance from Last Statement: £35.84\n- Balance on Statement Date: £89.67\n- Minimum Payment Due: £5.00\n- Payments Made: £199 total (two payments: £152 and £47)\n- Total Transactions: 44 (covering late July to mid-August 2025)"
    );
    when(translationService.processTranslationQuery(userInput, userLang)).thenReturn(mockResponse);

    // When
    TranslateServiceResponse response = translationController.handleQuery(userInput, userLang);

    // Then
    assertEquals(mockResponse.getUserInputLanguage(), response.getUserInputLanguage());
    assertEquals(mockResponse.getUserInputTextNative(), response.getUserInputTextNative());
    assertEquals(mockResponse.getModelTranslateToEnglish(), response.getModelTranslateToEnglish());
    assertEquals(mockResponse.getResponseDataInEnglish(), response.getResponseDataInEnglish());
    assertEquals(mockResponse.getModelTranslateResponseToNative(), response.getModelTranslateResponseToNative());
    }

    @Test
    void testHandleQuery_WithNullInput() {
        // Given
        String userInput = null;
        String userLang = "es";

        // When the service is called with null input, it should handle it gracefully
        // or throw an exception depending on implementation
    when(translationService.processTranslationQuery(userInput, userLang))
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
    when(translationService.processTranslationQuery(userInput, userLang))
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
    TranslateServiceResponse mockResponse = new TranslateServiceResponse(
        userLang,
        userInput,
        "fr",
        "Hello world",
        "Statement Overview\n- Statement Date: 21 Aug 2025\n- Due Date: 15 Sep 2025\n- Balance from Last Statement: £35.84\n- Balance on Statement Date: £89.67\n- Minimum Payment Due: £5.00\n- Payments Made: £199 total (two payments: £152 and £47)\n- Total Transactions: 44 (covering late July to mid-August 2025)",
        "Statement Overview\n- Statement Date: 21 Aug 2025\n- Due Date: 15 Sep 2025\n- Balance from Last Statement: £35.84\n- Balance on Statement Date: £89.67\n- Minimum Payment Due: £5.00\n- Payments Made: £199 total (two payments: £152 and £47)\n- Total Transactions: 44 (covering late July to mid-August 2025)"
    );
    when(translationService.processTranslationQuery(userInput, userLang)).thenReturn(mockResponse);

    // When
    TranslateServiceResponse response = translationController.handleQuery(userInput, userLang);

    // Then
    assertEquals(mockResponse.getUserInputLanguage(), response.getUserInputLanguage());
    assertEquals(mockResponse.getUserInputTextNative(), response.getUserInputTextNative());
    assertEquals(mockResponse.getModelTranslateToEnglish(), response.getModelTranslateToEnglish());
    assertEquals(mockResponse.getResponseDataInEnglish(), response.getResponseDataInEnglish());
    assertEquals(mockResponse.getModelTranslateResponseToNative(), response.getModelTranslateResponseToNative());
    }

    @Test
    void testHandleQuery_TranslationServiceThrowsException() {
        // Given
        String userInput = "Hola mundo";
        String userLang = "es";

    when(translationService.processTranslationQuery(userInput, userLang))
        .thenThrow(new RuntimeException("Translation service error"));

    // When & Then
    assertThrows(RuntimeException.class, () -> {
        translationController.handleQuery(userInput, userLang);
    });
    }

    @Test
    void testHandleQuery_VerifyMockAIResponse() {
        // Given
        String userInput = "Test input";
        String userLang = "en";
    TranslateServiceResponse mockResponse = new TranslateServiceResponse(
        userLang,
        userInput,
        "en",
        "Test input",
        "Statement Overview\n- Statement Date: 21 Aug 2025\n- Due Date: 15 Sep 2025\n- Balance from Last Statement: £35.84\n- Balance on Statement Date: £89.67\n- Minimum Payment Due: £5.00\n- Payments Made: £199 total (two payments: £152 and £47)\n- Total Transactions: 44 (covering late July to mid-August 2025)",
        "Statement Overview\n- Statement Date: 21 Aug 2025\n- Due Date: 15 Sep 2025\n- Balance from Last Statement: £35.84\n- Balance on Statement Date: £89.67\n- Minimum Payment Due: £5.00\n- Payments Made: £199 total (two payments: £152 and £47)\n- Total Transactions: 44 (covering late July to mid-August 2025)"
    );
    when(translationService.processTranslationQuery(userInput, userLang)).thenReturn(mockResponse);

    // When
    TranslateServiceResponse response = translationController.handleQuery(userInput, userLang);

    // Then
    assertEquals(mockResponse.getUserInputLanguage(), response.getUserInputLanguage());
    assertEquals(mockResponse.getUserInputTextNative(), response.getUserInputTextNative());
    assertEquals(mockResponse.getModelTranslateToEnglish(), response.getModelTranslateToEnglish());
    assertEquals(mockResponse.getResponseDataInEnglish(), response.getResponseDataInEnglish());
    assertEquals(mockResponse.getModelTranslateResponseToNative(), response.getModelTranslateResponseToNative());
    assertTrue(response.getResponseDataInEnglish().contains("Statement Overview"));
    assertTrue(response.getResponseDataInEnglish().contains("Statement Date"));
    assertTrue(response.getResponseDataInEnglish().contains("Due Date"));
    assertTrue(response.getResponseDataInEnglish().contains("Balance from Last Statement"));
    assertTrue(response.getResponseDataInEnglish().contains("Balance on Statement Date"));
    assertTrue(response.getResponseDataInEnglish().contains("Minimum Payment Due"));
    assertTrue(response.getResponseDataInEnglish().contains("Payments Made"));
    assertTrue(response.getResponseDataInEnglish().contains("Total Transactions"));
    }
}
