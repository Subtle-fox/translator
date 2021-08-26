package com.andyanika.translator.repository.remote.yandex

import com.google.gson.annotations.SerializedName
import java.util.*

internal class YandexTranslationResponse {
    @SerializedName("code")
    var code = 0

    @SerializedName("lang")
    var languageDirection: String? = null

    @SerializedName("text")
    var translatedText: ArrayList<String>? = null
}
