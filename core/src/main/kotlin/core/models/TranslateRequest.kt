package core.models

data class TranslateRequest(val text: String, val direction: TranslateDirection<LanguageCode>)
