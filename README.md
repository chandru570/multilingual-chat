# spending-translate
```markdown
# multilingual-chat
Multilingual Chat translates user input from non-English languages into English, processes it (mock AI), and returns the reply in the user's original language.

## Project Summary
Multilingual Chat is a Spring Boot application that detects the input language, translates text to English for processing, and returns the response translated back to the user's language. It leverages the Google Cloud Translation API for language detection and translation.

## Project Summary
Spending Translate is a Spring Boot application that translates user input from non-English languages to English, processes it (mock AI), and returns a response in the user's original language. It leverages Google Cloud Translation API for language detection and translation.

## Architecture
+- **Tech Stack:**
	# multilingual-chat

	Multilingual Chat is a Spring Boot application that detects the user's input language, translates the text to English for processing (mock AI), and returns the response translated back into the user's original language. It uses the Google Cloud Translation API for language detection and translation.

	## Project Summary
	Multilingual Chat provides a simple HTTP API to detect language, translate and process text, and return the processed result in the user's original language. The AI processing is currently mocked so you can iterate without external GenAI credentials.

	## Architecture
	- Tech Stack: Java, Spring Boot, Google Cloud Translation API
	- Main Service: `TranslationService` (handles detection, translation, orchestration)
	- Entry Point: `SpendingTranslateApplication.java`

	## Endpoints
	- `POST /api/translate`
	  - Request: `{ "text": "<user text>" }`
	  - Response: `{ "originalText": "...", "detectedLanguage": "...", "translatedText": "...", "responseText": "..." }`

	- `POST /api/speech` (optional)
	  - Request: audio file or speech data
	  - Response: transcribed text and translation

	## Developer Workflow
	- Build: `./gradlew build` (Windows: `gradlew.bat build`)
	- Run: `./gradlew bootRun` (Windows: `gradlew.bat bootRun`)
	- Test: `./gradlew test` (Windows: `gradlew.bat test`)
	  - Test reports: `build/reports/tests/test/index.html`

	## Configuration
	- Main config: `src/main/resources/application.properties`
	- Test config: `src/test/resources/application-test.properties`

	## Conventions
	- Services use `@Service` and constructor injection.
	- Translation results are wrapped in `TranslateServiceResponse`.
	- AI response is mocked in `TranslationService#mockAIResponse`.

	## Key Files
	- `src/main/java/com/poc/spending_translate/service/TranslationService.java`
	- `src/main/java/com/poc/spending_translate/controller/TranslationController.java`
	- `src/main/resources/application.properties`
	- `build.gradle`

	---

	If you'd like, I can also:
	- Run the test suite locally and share results
	- Rename classes/packages to match `multilingual-chat` naming
	- Add a short CONTRIBUTING or USAGE section

	Tell me which of those you'd like next.
- `src/main/java/com/poc/spending_translate/service/TranslationService.java`
