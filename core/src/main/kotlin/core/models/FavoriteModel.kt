package core.models

data class FavoriteModel(
    val id: Int,
    val translateResult: TranslateResult,
    val isFavorite: Boolean
)
