package com.poc.spending_translate.integration;

import com.poc.spending_translate.config.TestConfig;
import com.poc.spending_translate.service.SpeechRecognitionService;
import com.poc.spending_translate.service.TranslateServiceResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig.class)
class SpeechControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SpeechRecognitionService speechRecognitionService;

    @Test
    void testTranscribe_Success() throws Exception {
        // Given
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.wav", "audio/wav", "test audio data".getBytes()
        );
        String encoding = "LINEAR16";
        int sampleRateHertz = 16000;
        String languageCode = "en-US";
        TranslateServiceResponse expectedResponse = new TranslateServiceResponse(
            languageCode, "Hello world", languageCode, "Hello world", "Hello world", "Hello world"
        );
        when(speechRecognitionService.transcribe(any(byte[].class), eq(encoding), eq(sampleRateHertz), eq(languageCode)))
                .thenReturn(expectedResponse);

        // When & Then
        mockMvc.perform(multipart("/api/speech/transcribe")
                        .file(file)
                        .param("encoding", encoding)
                        .param("sampleRateHertz", String.valueOf(sampleRateHertz))
                        .param("languageCode", languageCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userInputTextNative").value("Hello world"));
    }

    @Test
    void testTranscribe_WithDefaultParameters() throws Exception {
        // Given
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.wav", "audio/wav", "test audio data".getBytes()
        );
        TranslateServiceResponse expectedResponse = new TranslateServiceResponse(
            "en-US", "Hello world", "en-US", "Hello world", "Hello world", "Hello world"
        );
        when(speechRecognitionService.transcribe(any(byte[].class), eq("LINEAR16"), eq(16000), eq("en-US")))
                .thenReturn(expectedResponse);

        // When & Then
        mockMvc.perform(multipart("/api/speech/transcribe")
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userInputTextNative").value("Hello world"));
    }

    @Test
    void testTranscribe_WithDifferentLanguage() throws Exception {
        // Given
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.wav", "audio/wav", "test audio data".getBytes()
        );
        String encoding = "LINEAR16";
        int sampleRateHertz = 16000;
        String languageCode = "es-ES";
        TranslateServiceResponse expectedResponse = new TranslateServiceResponse(
            languageCode, "Hola mundo", languageCode, "Hola mundo", "Hola mundo", "Hola mundo"
        );
        when(speechRecognitionService.transcribe(any(byte[].class), eq(encoding), eq(sampleRateHertz), eq(languageCode)))
                .thenReturn(expectedResponse);

        // When & Then
        mockMvc.perform(multipart("/api/speech/transcribe")
                        .file(file)
                        .param("encoding", encoding)
                        .param("sampleRateHertz", String.valueOf(sampleRateHertz))
                        .param("languageCode", languageCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userInputTextNative").value("Hola mundo"));
    }

    @Test
    void testTranscribe_WithDifferentEncoding() throws Exception {
        // Given
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.mp3", "audio/mpeg", "test audio data".getBytes()
        );
        String encoding = "MP3";
        int sampleRateHertz = 44100;
        String languageCode = "en-US";
        TranslateServiceResponse expectedResponse = new TranslateServiceResponse(
            languageCode, "Hello world", languageCode, "Hello world", "Hello world", "Hello world"
        );
        when(speechRecognitionService.transcribe(any(byte[].class), eq(encoding), eq(sampleRateHertz), eq(languageCode)))
                .thenReturn(expectedResponse);

        // When & Then
        mockMvc.perform(multipart("/api/speech/transcribe")
                        .file(file)
                        .param("encoding", encoding)
                        .param("sampleRateHertz", String.valueOf(sampleRateHertz))
                        .param("languageCode", languageCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userInputTextNative").value("Hello world"));
    }

    @Test
    void testTranscribe_WithEmptyFile() throws Exception {
        // Given
        MockMultipartFile file = new MockMultipartFile(
                "file", "empty.wav", "audio/wav", new byte[0]
        );
        String encoding = "LINEAR16";
        int sampleRateHertz = 16000;
        String languageCode = "en-US";
        TranslateServiceResponse expectedResponse = new TranslateServiceResponse(
            languageCode, "", languageCode, "", "", ""
        );
        when(speechRecognitionService.transcribe(any(byte[].class), eq(encoding), eq(sampleRateHertz), eq(languageCode)))
                .thenReturn(expectedResponse);

        // When & Then
        mockMvc.perform(multipart("/api/speech/transcribe")
                        .file(file)
                        .param("encoding", encoding)
                        .param("sampleRateHertz", String.valueOf(sampleRateHertz))
                        .param("languageCode", languageCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userInputTextNative").value(""));
    }

    @Test
    void testTranscribe_WithLargeFile() throws Exception {
        // Given
        byte[] largeAudioData = new byte[1024 * 1024]; // 1MB
        MockMultipartFile file = new MockMultipartFile(
                "file", "large.wav", "audio/wav", largeAudioData
        );
        String encoding = "LINEAR16";
        int sampleRateHertz = 16000;
        String languageCode = "en-US";
        TranslateServiceResponse expectedResponse = new TranslateServiceResponse(
            languageCode, "Large audio file transcription", languageCode, "Large audio file transcription", "Large audio file transcription", "Large audio file transcription"
        );
        when(speechRecognitionService.transcribe(any(byte[].class), eq(encoding), eq(sampleRateHertz), eq(languageCode)))
                .thenReturn(expectedResponse);

        // When & Then
        mockMvc.perform(multipart("/api/speech/transcribe")
                        .file(file)
                        .param("encoding", encoding)
                        .param("sampleRateHertz", String.valueOf(sampleRateHertz))
                        .param("languageCode", languageCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userInputTextNative").value("Large audio file transcription"));
    }

    @Test
    void testTranscribe_ServiceThrowsIOException() throws Exception {
        // Given
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.wav", "audio/wav", "test audio data".getBytes()
        );
        String encoding = "LINEAR16";
        int sampleRateHertz = 16000;
        String languageCode = "en-US";

        when(speechRecognitionService.transcribe(any(byte[].class), eq(encoding), eq(sampleRateHertz), eq(languageCode)))
                .thenThrow(new IOException("Speech recognition failed"));

        // When & Then
        // The controller method declares throws IOException, so it will propagate the exception
        // This will result in a 500 Internal Server Error
        try {
            mockMvc.perform(multipart("/api/speech/transcribe")
                            .file(file)
                            .param("encoding", encoding)
                            .param("sampleRateHertz", String.valueOf(sampleRateHertz))
                            .param("languageCode", languageCode))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            // IOException is expected to be thrown by the controller
            // This is the expected behavior since the controller method declares throws IOException
            assertTrue(e.getCause() instanceof IOException || e instanceof IOException);
        }
    }

    @Test
    void testTranscribe_MissingFile() throws Exception {
        // When & Then
        mockMvc.perform(multipart("/api/speech/transcribe")
                        .param("encoding", "LINEAR16")
                        .param("sampleRateHertz", "16000")
                        .param("languageCode", "en-US"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testTranscribe_WithInvalidSampleRate() throws Exception {
        // Given
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.wav", "audio/wav", "test audio data".getBytes()
        );

        // When & Then
        mockMvc.perform(multipart("/api/speech/transcribe")
                        .file(file)
                        .param("encoding", "LINEAR16")
                        .param("sampleRateHertz", "invalid")
                        .param("languageCode", "en-US"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testTranscribe_WithMultipleLanguages() throws Exception {
        // Given
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.wav", "audio/wav", "test audio data".getBytes()
        );
        String encoding = "LINEAR16";
        int sampleRateHertz = 16000;
        String[] languages = {"en-US", "es-ES", "fr-FR", "de-DE"};

        for (String languageCode : languages) {
            TranslateServiceResponse expectedResponse = new TranslateServiceResponse(
                languageCode, "Transcription in " + languageCode, languageCode, "Transcription in " + languageCode, "Transcription in " + languageCode, "Transcription in " + languageCode
            );
            when(speechRecognitionService.transcribe(any(byte[].class), eq(encoding), eq(sampleRateHertz), eq(languageCode)))
                    .thenReturn(expectedResponse);

            // When & Then
            mockMvc.perform(multipart("/api/speech/transcribe")
                            .file(file)
                            .param("encoding", encoding)
                            .param("sampleRateHertz", String.valueOf(sampleRateHertz))
                            .param("languageCode", languageCode))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.userInputTextNative").value("Transcription in " + languageCode));
        }
    }
}
