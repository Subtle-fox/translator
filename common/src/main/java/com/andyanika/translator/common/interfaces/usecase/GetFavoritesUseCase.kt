package com.andyanika.translator.common.interfaces.usecase

import com.andyanika.translator.common.models.FavoriteModel
import kotlinx.coroutines.flow.Flow

interface GetFavoritesUseCase {
    fun run(limit: Int): Flow<List<FavoriteModel>>
}
