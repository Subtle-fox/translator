package com.andyanika.translator.repository.remote

import com.andyanika.translator.common.models.TranslateRequest
import com.andyanika.translator.common.models.TranslateResult
import javax.inject.Inject

internal class YandexModelsAdapter @Inject constructor() {
    fun convert(request: TranslateRequest, response: YandexTranslationResponse): TranslateResult {
        val res = StringBuilder()
        for (s in response.translatedText!!) {
            res.append(s).append('\n')
        }
        return TranslateResult(request.text, res.toString().trim { it <= ' ' }, request.direction)
    }

    // Yandex's specific case:
    fun isTranslationFound(result: TranslateResult): Boolean {
        return !result.textDst.isEmpty() && !result.textSrc.equals(result.textDst, ignoreCase = true)
    }
}
