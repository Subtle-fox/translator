package core.models.ui

import core.models.LanguageCode
import core.models.TranslateDirection
import core.models.TranslateRequest
import core.models.TranslateResult

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
