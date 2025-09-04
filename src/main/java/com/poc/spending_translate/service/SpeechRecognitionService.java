package com.poc.spending_translate.service;

import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SpeechRecognitionService {

    public String transcribe(byte[] audioData, String encoding, int sampleRateHertz, String languageCode)
            throws IOException {
        try (SpeechClient client = SpeechClient.create()) {
            RecognitionConfig config = RecognitionConfig.newBuilder()
                    .setEncoding(RecognitionConfig.AudioEncoding.valueOf(encoding))
                    .setSampleRateHertz(sampleRateHertz)
                    .setLanguageCode(languageCode)
                    .build();

            RecognitionAudio audio = RecognitionAudio.newBuilder()
                    .setContent(ByteString.copyFrom(audioData))
                    .build();

            RecognizeResponse response = client.recognize(config, audio);
            List<SpeechRecognitionResult> results = response.getResultsList();

            // Concatenate all transcriptions
            StringBuilder transcript = new StringBuilder();
            for (SpeechRecognitionResult result : results) {
                transcript.append(result.getAlternativesList().get(0).getTranscript());
                transcript.append(" ");
            }
            return transcript.toString().trim();
        }
    }
}