package com.poc.spending_translate.service;

public class TranslateServiceResponse {
    private String userInputLanguage;
    private String userInputTextNative;
    private String modelDetectLanguage;
    private String modelTranslateToEnglish;
    private String responseDataInEnglish;
    private String modelTranslateResponseToNative;

    public TranslateServiceResponse(String userInputLanguage, String userInputTextNative, String modelDetectLanguage,
                                    String modelTranslateToEnglish, String responseDataInEnglish, String modelTranslateResponseToNative) {
        this.userInputLanguage = userInputLanguage;
        this.userInputTextNative = userInputTextNative;
        this.modelDetectLanguage = modelDetectLanguage;
        this.modelTranslateToEnglish = modelTranslateToEnglish;
        this.responseDataInEnglish = responseDataInEnglish;
        this.modelTranslateResponseToNative = modelTranslateResponseToNative;
    }

    public String getUserInputLanguage() { return userInputLanguage; }
    public String getUserInputTextNative() { return userInputTextNative; }
    public String getModelDetectLanguage() { return modelDetectLanguage; }
    public String getModelTranslateToEnglish() { return modelTranslateToEnglish; }
    public String getResponseDataInEnglish() { return responseDataInEnglish; }
    public String getModelTranslateResponseToNative() { return modelTranslateResponseToNative; }

    public void setUserInputLanguage(String userInputLanguage) { this.userInputLanguage = userInputLanguage; }
    public void setUserInputTextNative(String userInputTextNative) { this.userInputTextNative = userInputTextNative; }
    public void setModelDetectLanguage(String modelDetectLanguage) { this.modelDetectLanguage = modelDetectLanguage; }
    public void setModelTranslateToEnglish(String modelTranslateToEnglish) { this.modelTranslateToEnglish = modelTranslateToEnglish; }
    public void setResponseDataInEnglish(String responseDataInEnglish) { this.responseDataInEnglish = responseDataInEnglish; }
    public void setModelTranslateResponseToNative(String modelTranslateResponseToNative) { this.modelTranslateResponseToNative = modelTranslateResponseToNative; }
}
