package core.models

data class TranslateResult(
    val textSrc: String,
    val textDst: String,
    val direction: TranslateDirection<LanguageCode>
)
