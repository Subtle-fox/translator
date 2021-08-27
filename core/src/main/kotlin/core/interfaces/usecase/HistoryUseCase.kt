package core.interfaces.usecase

import core.models.FavoriteModel
import kotlinx.coroutines.flow.Flow

interface HistoryUseCase {
    fun run(filterStr: String, limit: Int): Flow<List<FavoriteModel>>
}
