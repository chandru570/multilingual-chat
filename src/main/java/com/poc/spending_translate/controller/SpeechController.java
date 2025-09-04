package com.poc.spending_translate.controller;

import com.poc.spending_translate.service.SpeechRecognitionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/speech")
public class SpeechController {

    private final SpeechRecognitionService speechService;

    public SpeechController(SpeechRecognitionService speechService) {
        this.speechService = speechService;
    }

    @PostMapping(value = "/transcribe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> transcribe(
            @RequestPart("file") MultipartFile file,
            @RequestParam(defaultValue = "LINEAR16") String encoding,
            @RequestParam(defaultValue = "16000") int sampleRateHertz,
            @RequestParam(defaultValue = "en-US") String languageCode
    ) throws IOException {
        byte[] audioBytes = file.getBytes();
        String text = speechService.transcribe(audioBytes, encoding, sampleRateHertz, languageCode);
        return ResponseEntity.ok(text);
    }
}