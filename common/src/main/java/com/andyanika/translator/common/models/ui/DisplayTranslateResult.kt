package com.andyanika.translator.common.models.ui

import com.andyanika.translator.common.models.LanguageCode
import com.andyanika.translator.common.models.TranslateDirection
import com.andyanika.translator.common.models.TranslateRequest
import com.andyanika.translator.common.models.TranslateResult

class DisplayTranslateResult {
    val textSrc: String
    val textTranslated: String?
    val direction: TranslateDirection<LanguageCode>
    var isError: Boolean
    var isFound: Boolean
    var isOffline: Boolean

    constructor(result: TranslateResult, isOffline: Boolean) {
        textSrc = result.textSrc
        textTranslated = result.textDst
        direction = result.direction
        this.isOffline = isOffline
        isError = false
        isFound = true
    }

    constructor(
        textSrc: String,
        textTranslated: String?,
        direction: TranslateDirection<LanguageCode>,
        isOffline: Boolean
    ) {
        this.textSrc = textSrc
        this.textTranslated = textTranslated
        this.direction = direction
        this.isOffline = isOffline
        isError = false
        isFound = true
    }

    companion object {
        fun createEmptyResult(request: TranslateRequest, isError: Boolean): DisplayTranslateResult {
            val noResult = DisplayTranslateResult(request.text, null, request.direction, false)
            noResult.isError = isError
            noResult.isFound = false
            return noResult
        }
    }
}
