package com.andyanika.translator.common.models

data class TranslateRequest(val text: String, val direction: TranslateDirection<LanguageCode>)