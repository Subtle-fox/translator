package com.andyanika.translator.repository.remote.yandex

import com.andyanika.translator.common.models.TranslateRequest
import com.andyanika.translator.common.models.TranslateResult

internal class YandexModelsAdapter constructor() {
    fun convert(request: TranslateRequest, response: YandexTranslationResponse): TranslateResult {
        val res = StringBuilder()
        for (s in response.translatedText!!) {
            res.append(s).append('\n')
        }
        return TranslateResult(request.text, res.toString().trim { it <= ' ' }, request.direction)
    }

    // Yandex's specific case:
    fun isTranslationFound(result: TranslateResult): Boolean {
        return result.textDst.isNotEmpty() && !result.textSrc.equals(result.textDst, ignoreCase = true)
    }
}
