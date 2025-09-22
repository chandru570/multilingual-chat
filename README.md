# spending-translate
Translate user input from non-English to English and return response back in non-English
# Spending Translate

## Project Summary
Spending Translate is a Spring Boot application that translates user input from non-English languages to English, processes it (mock AI), and returns a response in the user's original language. It leverages Google Cloud Translation API for language detection and translation.

## Architecture
+- **Tech Stack:**
	- Java
	- Spring Boot
	- Google Cloud Translation API
	- Google Cloud Speech API
	- Google GenAI
- **Main Service:**
	- `TranslationService` orchestrates language detection, translation, and mock AI response.
- **Entry Point:**
	- `SpendingTranslateApplication.java`

## Dependencies
- Spring Boot Starter Web
- Spring Boot Starter Test
- Google Cloud Translation API (`com.google.cloud:google-cloud-translate`)
- Google Cloud Speech API (`com.google.cloud:google-cloud-speech`)
- Google GenAI (`com.google.genai:google-genai:1.0.0`)
- Jackson (JSON serialization)
- JUnit (testing)

## Endpoints Exposed

### Translation
- `POST /api/translate`
	- Request: `{ "text": "<user text>" }`
	- Response: `{ "originalText": "...", "translatedText": "...", "responseText": "..." }`

### Speech Recognition (if implemented)
- `POST /api/speech`
	- Request: Audio file or speech data
	- Response: Transcribed text and translation

## Developer Workflow
- **Build:** `./gradlew build` (or `gradlew.bat build` on Windows)
- **Run:** `./gradlew bootRun` (or `gradlew.bat bootRun`)
- **Test:** `./gradlew test` (or `gradlew.bat test`)
	- Test reports: `build/reports/tests/test/index.html`

## Configuration
- Main config: `src/main/resources/application.properties`
- Test config: `src/test/resources/application-test.properties`

## Patterns & Conventions
- Services use `@Service` annotation and constructor injection.
- Always detect language before translating.
- All translation results are wrapped in `TranslateServiceResponse`.
- AI response is currently mocked in `TranslationService#mockAIResponse`.

## Example Workflow
1. User submits non-English input.
2. `TranslationService` detects language, translates to English.
3. English text sent to AI (mocked).
4. AI response translated back to user's language.
5. Response returned as `TranslateServiceResponse`.

## Key Files
- `src/main/java/com/poc/spending_translate/service/TranslationService.java`
- `src/main/resources/application.properties`
- `build.gradle`
- `README.md`

---
For questions or unclear conventions, review the above files or ask for clarification.
