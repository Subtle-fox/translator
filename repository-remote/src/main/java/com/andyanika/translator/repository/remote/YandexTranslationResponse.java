package com.andyanika.translator.repository.remote;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

class YandexTranslationResponse {
    @SerializedName("code")
    int code;

    @SerializedName("lang")
    String languageDirection;

    @SerializedName("text")
    ArrayList<String> translatedText;
}
