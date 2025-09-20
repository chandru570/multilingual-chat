package com.poc.spending_translate.controller;

import com.poc.spending_translate.service.TranslationService;
import com.poc.spending_translate.service.TranslateServiceResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/translate")
public class TranslationController {

    private final TranslationService translationService;

    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @GetMapping("/query")
    public TranslateServiceResponse handleQuery(@RequestParam String userInput,
                                                @RequestParam(required = false) String userLang) {
        return translationService.processTranslationQuery(userInput, userLang);
    }
}
