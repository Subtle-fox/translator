package core.models.ui

import core.models.LanguageCode

class DisplayLanguageModel(
    val code: LanguageCode,
    val description: String,
    val isSelected: Boolean
)
