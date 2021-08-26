package com.andyanika.usecases

import com.andyanika.translator.common.interfaces.LocalRepository
import com.andyanika.translator.common.interfaces.usecase.GetFavoritesUseCase
import com.andyanika.translator.common.models.FavoriteModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetFavoritesUseCaseImpl(
    private val repository: LocalRepository
) : GetFavoritesUseCase {
    override fun run(limit: Int): Flow<List<FavoriteModel>> {
        return repository
            .getFavorites()
            .map { it.subList(0, limit) }
    }
}
