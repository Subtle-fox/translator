package com.andyanika.translator.network;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TranslationResponse {
    @SerializedName("code")
    int code;

    @SerializedName("lang")
    String languageDirection;

    @SerializedName("text")
    ArrayList<String> translatedText;
}
