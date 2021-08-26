package com.andyanika.translator.common.models

data class FavoriteModel(
    val id: Int,
    val translateResult: TranslateResult,
    val isFavorite: Boolean
)
