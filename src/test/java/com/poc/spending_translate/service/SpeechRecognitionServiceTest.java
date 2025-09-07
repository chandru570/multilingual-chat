package com.poc.spending_translate.service;

import com.google.cloud.speech.v1.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpeechRecognitionServiceTest {

    @Mock
    private SpeechClient speechClient;

    @Mock
    private RecognizeResponse recognizeResponse;

    @Mock
    private SpeechRecognitionResult recognitionResult;

    @Mock
    private SpeechRecognitionAlternative alternative;

    private SpeechRecognitionService speechService;

    @BeforeEach
    void setUp() {
        speechService = new SpeechRecognitionService();
    }

    @Test
    void testTranscribe_Success() throws IOException {
        // Given
        byte[] audioData = "test audio data".getBytes();
        String encoding = "LINEAR16";
        int sampleRateHertz = 16000;
        String languageCode = "en-US";
        String expectedTranscript = "Hello world";

        try (MockedStatic<SpeechClient> mockedSpeechClient = mockStatic(SpeechClient.class)) {
            mockedSpeechClient.when(SpeechClient::create).thenReturn(speechClient);
            
            when(speechClient.recognize(any(RecognitionConfig.class), any(RecognitionAudio.class)))
                    .thenReturn(recognizeResponse);
            when(recognizeResponse.getResultsList()).thenReturn(Arrays.asList(recognitionResult));
            when(recognitionResult.getAlternativesList()).thenReturn(Arrays.asList(alternative));
            when(alternative.getTranscript()).thenReturn(expectedTranscript);

            // When
            String result = speechService.transcribe(audioData, encoding, sampleRateHertz, languageCode);

            // Then
            assertEquals(expectedTranscript, result);
            verify(speechClient).recognize(any(RecognitionConfig.class), any(RecognitionAudio.class));
        }
    }

    @Test
    void testTranscribe_MultipleResults() throws IOException {
        // Given
        byte[] audioData = "test audio data".getBytes();
        String encoding = "LINEAR16";
        int sampleRateHertz = 16000;
        String languageCode = "en-US";
        String expectedTranscript = "Hello world How are you";

        SpeechRecognitionResult result1 = mock(SpeechRecognitionResult.class);
        SpeechRecognitionResult result2 = mock(SpeechRecognitionResult.class);
        SpeechRecognitionAlternative alt1 = mock(SpeechRecognitionAlternative.class);
        SpeechRecognitionAlternative alt2 = mock(SpeechRecognitionAlternative.class);

        when(alt1.getTranscript()).thenReturn("Hello world");
        when(alt2.getTranscript()).thenReturn("How are you");
        when(result1.getAlternativesList()).thenReturn(Arrays.asList(alt1));
        when(result2.getAlternativesList()).thenReturn(Arrays.asList(alt2));

        try (MockedStatic<SpeechClient> mockedSpeechClient = mockStatic(SpeechClient.class)) {
            mockedSpeechClient.when(SpeechClient::create).thenReturn(speechClient);
            
            when(speechClient.recognize(any(RecognitionConfig.class), any(RecognitionAudio.class)))
                    .thenReturn(recognizeResponse);
            when(recognizeResponse.getResultsList()).thenReturn(Arrays.asList(result1, result2));

            // When
            String result = speechService.transcribe(audioData, encoding, sampleRateHertz, languageCode);

            // Then
            assertEquals(expectedTranscript, result);
        }
    }

    @Test
    void testTranscribe_EmptyResults() throws IOException {
        // Given
        byte[] audioData = "test audio data".getBytes();
        String encoding = "LINEAR16";
        int sampleRateHertz = 16000;
        String languageCode = "en-US";

        try (MockedStatic<SpeechClient> mockedSpeechClient = mockStatic(SpeechClient.class)) {
            mockedSpeechClient.when(SpeechClient::create).thenReturn(speechClient);
            
            when(speechClient.recognize(any(RecognitionConfig.class), any(RecognitionAudio.class)))
                    .thenReturn(recognizeResponse);
            when(recognizeResponse.getResultsList()).thenReturn(Arrays.asList());

            // When
            String result = speechService.transcribe(audioData, encoding, sampleRateHertz, languageCode);

            // Then
            assertEquals("", result);
        }
    }

    @Test
    void testTranscribe_IOException() throws IOException {
        // Given
        byte[] audioData = "test audio data".getBytes();
        String encoding = "LINEAR16";
        int sampleRateHertz = 16000;
        String languageCode = "en-US";

        try (MockedStatic<SpeechClient> mockedSpeechClient = mockStatic(SpeechClient.class)) {
            mockedSpeechClient.when(SpeechClient::create).thenThrow(new IOException("Connection failed"));

            // When & Then
            assertThrows(IOException.class, () -> {
                speechService.transcribe(audioData, encoding, sampleRateHertz, languageCode);
            });
        }
    }

    @Test
    void testTranscribe_WithNullAudioData() throws IOException {
        // Given
        byte[] audioData = null;
        String encoding = "LINEAR16";
        int sampleRateHertz = 16000;
        String languageCode = "en-US";

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            speechService.transcribe(audioData, encoding, sampleRateHertz, languageCode);
        });
    }

    @Test
    void testTranscribe_WithEmptyAudioData() throws IOException {
        // Given
        byte[] audioData = new byte[0];
        String encoding = "LINEAR16";
        int sampleRateHertz = 16000;
        String languageCode = "en-US";

        try (MockedStatic<SpeechClient> mockedSpeechClient = mockStatic(SpeechClient.class)) {
            mockedSpeechClient.when(SpeechClient::create).thenReturn(speechClient);
            
            when(speechClient.recognize(any(RecognitionConfig.class), any(RecognitionAudio.class)))
                    .thenReturn(recognizeResponse);
            when(recognizeResponse.getResultsList()).thenReturn(Arrays.asList());

            // When
            String result = speechService.transcribe(audioData, encoding, sampleRateHertz, languageCode);

            // Then
            assertEquals("", result);
        }
    }
}
