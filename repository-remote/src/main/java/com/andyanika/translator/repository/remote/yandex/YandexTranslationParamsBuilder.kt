package com.andyanika.translator.repository.remote.yandex

import com.andyanika.translator.common.models.LanguageCode
import com.andyanika.translator.common.models.TranslateDirection
import java.util.*

internal class YandexTranslationParamsBuilder {
    fun buildParam(direction: TranslateDirection<LanguageCode>): String {
        return String
            .format("%s-%s", direction.src.toString(), direction.dst.toString())
            .lowercase(Locale.getDefault())
    }
}
