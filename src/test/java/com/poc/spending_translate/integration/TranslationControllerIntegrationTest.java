package com.poc.spending_translate.integration;

import com.poc.spending_translate.config.TestConfig;
import com.poc.spending_translate.service.TranslationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig.class)
class TranslationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TranslationService translationService;

    @Test
    void testTranslateQuery_Success() throws Exception {
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

        // When & Then
        mockMvc.perform(get("/api/translate/query")
                        .param("userInput", userInput)
                        .param("userLang", userLang))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }

    @Test
    void testTranslateQuery_WithFrenchInput() throws Exception {
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

        // When & Then
        mockMvc.perform(get("/api/translate/query")
                        .param("userInput", userInput)
                        .param("userLang", userLang))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }

    @Test
    void testTranslateQuery_WithGermanInput() throws Exception {
        // Given
        String userInput = "Hallo Welt";
        String userLang = "de";
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

        // When & Then
        mockMvc.perform(get("/api/translate/query")
                        .param("userInput", userInput)
                        .param("userLang", userLang))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }

    @Test
    void testTranslateQuery_MissingUserInput() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/translate/query")
                        .param("userLang", "es"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testTranslateQuery_MissingUserLang() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/translate/query")
                        .param("userInput", "Hello"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testTranslateQuery_EmptyParameters() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/translate/query")
                        .param("userInput", "")
                        .param("userLang", ""))
                .andExpect(status().isOk());
    }

    @Test
    void testTranslateQuery_WithSpecialCharacters() throws Exception {
        // Given
        String userInput = "¡Hola! ¿Cómo estás?";
        String userLang = "es";
        String translatedToEnglish = "Hello! How are you?";
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

        // When & Then
        mockMvc.perform(get("/api/translate/query")
                        .param("userInput", userInput)
                        .param("userLang", userLang))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }

    @Test
    void testTranslateQuery_WithLongInput() throws Exception {
        // Given
        String userInput = "This is a very long input text that contains multiple sentences and should be properly translated and processed by the system without any issues.";
        String userLang = "en";
        String translatedToEnglish = userInput; // Same as input since it's already in English
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

        // When & Then
        mockMvc.perform(get("/api/translate/query")
                        .param("userInput", userInput)
                        .param("userLang", userLang))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }
}
