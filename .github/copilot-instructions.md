# Copilot Instructions for spending-translate

## Project Overview
- **Purpose:** Translate user input from non-English to English, process it, and return a response in the user's original language.
- **Tech Stack:** Java, Spring Boot, Google Cloud Translation API.
- **Main Service:** `TranslationService` orchestrates language detection, translation, and mock AI response.

## Architecture & Key Components
- **Service Layer:**
  - `TranslationService` (see `src/main/java/com/poc/spending_translate/service/TranslationService.java`)
    - Detects input language
    - Translates to English
    - Sends to AI (mocked)
    - Translates response back to user's language
    - Returns a `TranslateServiceResponse` object
- **Resource Files:**
  - `application.properties` for main config
  - `application-test.properties` for test config
- **Entry Point:**
  - Main class in `src/main/java/com/poc/spending_translate/SpendingTranslateApplication.java`

## Developer Workflows
- **Build:**
  - Use Gradle wrapper: `./gradlew build` (or `gradlew.bat build` on Windows)
- **Run:**
  - `./gradlew bootRun` (or `gradlew.bat bootRun`)
- **Test:**
  - `./gradlew test` (or `gradlew.bat test`)
  - Test reports: `build/reports/tests/test/index.html`

## Patterns & Conventions
- **Service Construction:**
  - Services are annotated with `@Service` and use constructor injection for dependencies.
- **Translation Logic:**
  - Always detect language before translating.
  - Use Google Cloud Translation API for all translation steps.
- **Mock AI Integration:**
  - The AI response is currently mocked in `TranslationService#mockAIResponse`. Replace with real integration as needed.
- **Response Model:**
  - All translation results are wrapped in `TranslateServiceResponse`.

## Integration Points
- **External:**
  - Google Cloud Translation API (via `com.google.cloud.translate`)
- **Internal:**
  - Service classes communicate via method calls, not events or messaging.

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
For questions or unclear conventions, ask for clarification or review the above files for examples.
