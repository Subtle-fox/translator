package com.andyanika.translator.repository.remote

import com.andyanika.translator.common.models.LanguageCode
import com.andyanika.translator.common.models.TranslateDirection
import java.util.*
import javax.inject.Inject

internal class YandexTranslationParamsBuilder @Inject constructor() {
    fun buildParam(direction: TranslateDirection<LanguageCode>): String {
        return String
            .format("%s-%s", direction.src.toString(), direction.dst.toString())
            .lowercase(Locale.getDefault())
    }
}
