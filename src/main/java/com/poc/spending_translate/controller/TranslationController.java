package com.poc.spending_translate.controller;

import com.poc.spending_translate.service.TranslationService;
import com.poc.spending_translate.service.TranslateServiceResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/translate")
@CrossOrigin(origins = "*")
public class TranslationController {

    private final TranslationService translationService;

    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @GetMapping("/query")
    public TranslateServiceResponse handleQuery(@RequestParam String userInput,
                                                @RequestParam(required = false) String userLang) {
    String requestUrl = "/api/translate/query?userInput=" + userInput + (userLang != null ? "&userLang=" + userLang : "");
    System.out.println("Request URL: " + requestUrl);
    TranslateServiceResponse response = translationService.processTranslationQuery(userInput, userLang);
    System.out.println("Response JSON: " + response);
    return response;
    }
}
