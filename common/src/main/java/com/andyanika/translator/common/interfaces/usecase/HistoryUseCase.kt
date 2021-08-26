package com.andyanika.translator.common.interfaces.usecase

import com.andyanika.translator.common.models.FavoriteModel
import kotlinx.coroutines.flow.Flow

interface HistoryUseCase {
    fun run(filterStr: String, limit: Int): Flow<List<FavoriteModel>>
}
