package com.andyanika.translator.feature.translate.mvi

import core.models.LanguageCode

data class TranslationViewState(
    val isNotFound: Boolean,
    val isOffline: Boolean,
    val isLoading: Boolean,
    val isError: Boolean,
    val canClear: Boolean,
    val srcCode: LanguageCode,
    val dstCode: LanguageCode,
    val output: String,
    val input: String
) {
    companion object {
        val INITIAL = TranslationViewState(
            isNotFound = false,
            isOffline = false,
            isLoading = false,
            isError = false,
            canClear = false,
            srcCode = LanguageCode.RU,
            dstCode = LanguageCode.EN,
            input = "",
            output = ""
        )
    }
}
