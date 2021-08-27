package core.interfaces.usecase

import core.models.FavoriteModel
import kotlinx.coroutines.flow.Flow

interface GetFavoritesUseCase {
    fun run(): Flow<List<FavoriteModel>>
}
