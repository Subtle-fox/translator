package com.andyanika.translator.feature.translate.mvi

sealed class TranslationAction() {
    object Clear : TranslationAction()
    object Retry : TranslationAction()
    object SwapDirection : TranslationAction()
    data class Translate(val text: String): TranslationAction()
}
