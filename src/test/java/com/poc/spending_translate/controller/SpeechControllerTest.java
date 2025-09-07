package com.poc.spending_translate.controller;

import com.poc.spending_translate.service.SpeechRecognitionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpeechControllerTest {

    @Mock
    private SpeechRecognitionService speechService;

    private SpeechController speechController;

    @BeforeEach
    void setUp() {
        speechController = new SpeechController(speechService);
    }

    @Test
    void testTranscribe_Success() throws IOException {
        // Given
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.wav", "audio/wav", "test audio data".getBytes()
        );
        String encoding = "LINEAR16";
        int sampleRateHertz = 16000;
        String languageCode = "en-US";
        String expectedTranscript = "Hello world";

        when(speechService.transcribe(any(byte[].class), eq(encoding), eq(sampleRateHertz), eq(languageCode)))
                .thenReturn(expectedTranscript);

        // When
        ResponseEntity<String> response = speechController.transcribe(file, encoding, sampleRateHertz, languageCode);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTranscript, response.getBody());
        verify(speechService).transcribe(any(byte[].class), eq(encoding), eq(sampleRateHertz), eq(languageCode));
    }

    @Test
    void testTranscribe_WithDefaultParameters() throws IOException {
        // Given
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.wav", "audio/wav", "test audio data".getBytes()
        );
        String expectedTranscript = "Hello world";

        when(speechService.transcribe(any(byte[].class), eq("LINEAR16"), eq(16000), eq("en-US")))
                .thenReturn(expectedTranscript);

        // When
        ResponseEntity<String> response = speechController.transcribe(file, "LINEAR16", 16000, "en-US");

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTranscript, response.getBody());
        verify(speechService).transcribe(any(byte[].class), eq("LINEAR16"), eq(16000), eq("en-US"));
    }

    @Test
    void testTranscribe_WithDifferentEncoding() throws IOException {
        // Given
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.mp3", "audio/mpeg", "test audio data".getBytes()
        );
        String encoding = "MP3";
        int sampleRateHertz = 44100;
        String languageCode = "es-ES";
        String expectedTranscript = "Hola mundo";

        when(speechService.transcribe(any(byte[].class), eq(encoding), eq(sampleRateHertz), eq(languageCode)))
                .thenReturn(expectedTranscript);

        // When
        ResponseEntity<String> response = speechController.transcribe(file, encoding, sampleRateHertz, languageCode);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTranscript, response.getBody());
        verify(speechService).transcribe(any(byte[].class), eq(encoding), eq(sampleRateHertz), eq(languageCode));
    }

    @Test
    void testTranscribe_WithEmptyFile() throws IOException {
        // Given
        MockMultipartFile file = new MockMultipartFile(
                "file", "empty.wav", "audio/wav", new byte[0]
        );
        String encoding = "LINEAR16";
        int sampleRateHertz = 16000;
        String languageCode = "en-US";
        String expectedTranscript = "";

        when(speechService.transcribe(any(byte[].class), eq(encoding), eq(sampleRateHertz), eq(languageCode)))
                .thenReturn(expectedTranscript);

        // When
        ResponseEntity<String> response = speechController.transcribe(file, encoding, sampleRateHertz, languageCode);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTranscript, response.getBody());
        verify(speechService).transcribe(any(byte[].class), eq(encoding), eq(sampleRateHertz), eq(languageCode));
    }

    @Test
    void testTranscribe_ServiceThrowsIOException() throws IOException {
        // Given
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.wav", "audio/wav", "test audio data".getBytes()
        );
        String encoding = "LINEAR16";
        int sampleRateHertz = 16000;
        String languageCode = "en-US";

        when(speechService.transcribe(any(byte[].class), eq(encoding), eq(sampleRateHertz), eq(languageCode)))
                .thenThrow(new IOException("Speech recognition failed"));

        // When & Then
        assertThrows(IOException.class, () -> {
            speechController.transcribe(file, encoding, sampleRateHertz, languageCode);
        });

        verify(speechService).transcribe(any(byte[].class), eq(encoding), eq(sampleRateHertz), eq(languageCode));
    }

    @Test
    void testTranscribe_WithNullFile() {
        // Given
        MockMultipartFile file = null;
        String encoding = "LINEAR16";
        int sampleRateHertz = 16000;
        String languageCode = "en-US";

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            speechController.transcribe(file, encoding, sampleRateHertz, languageCode);
        });
    }

    @Test
    void testTranscribe_VerifyFileBytesArePassed() throws IOException {
        // Given
        byte[] expectedBytes = "specific audio data".getBytes();
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.wav", "audio/wav", expectedBytes
        );
        String encoding = "LINEAR16";
        int sampleRateHertz = 16000;
        String languageCode = "en-US";
        String expectedTranscript = "Hello world";

        when(speechService.transcribe(eq(expectedBytes), eq(encoding), eq(sampleRateHertz), eq(languageCode)))
                .thenReturn(expectedTranscript);

        // When
        ResponseEntity<String> response = speechController.transcribe(file, encoding, sampleRateHertz, languageCode);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTranscript, response.getBody());
        verify(speechService).transcribe(eq(expectedBytes), eq(encoding), eq(sampleRateHertz), eq(languageCode));
    }

    @Test
    void testTranscribe_WithLargeFile() throws IOException {
        // Given
        byte[] largeAudioData = new byte[1024 * 1024]; // 1MB
        MockMultipartFile file = new MockMultipartFile(
                "file", "large.wav", "audio/wav", largeAudioData
        );
        String encoding = "LINEAR16";
        int sampleRateHertz = 16000;
        String languageCode = "en-US";
        String expectedTranscript = "Large audio file transcription";

        when(speechService.transcribe(eq(largeAudioData), eq(encoding), eq(sampleRateHertz), eq(languageCode)))
                .thenReturn(expectedTranscript);

        // When
        ResponseEntity<String> response = speechController.transcribe(file, encoding, sampleRateHertz, languageCode);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTranscript, response.getBody());
        verify(speechService).transcribe(eq(largeAudioData), eq(encoding), eq(sampleRateHertz), eq(languageCode));
    }
}
