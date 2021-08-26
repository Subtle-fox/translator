package com.andyanika.translator.common.models

data class TranslateResult(
    val textSrc: String,
    val textDst: String,
    val direction: TranslateDirection<LanguageCode>
)
