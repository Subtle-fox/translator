package com.andyanika.usecases

import core.interfaces.LocalRepository
import core.interfaces.usecase.GetFavoritesUseCase
import core.models.FavoriteModel
import kotlinx.coroutines.flow.Flow

internal class GetFavoritesUseCaseImpl(
    private val repository: LocalRepository
) : GetFavoritesUseCase {
    override fun run(): Flow<List<FavoriteModel>> {
        return repository.getFavorites()
    }
}
