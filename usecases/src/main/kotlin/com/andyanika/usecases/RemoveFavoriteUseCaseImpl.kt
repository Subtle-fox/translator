package com.andyanika.usecases

import core.interfaces.LocalRepository
import core.interfaces.usecase.RemoveFavoriteUseCase

internal class RemoveFavoriteUseCaseImpl(
    private val repository: LocalRepository,
) : RemoveFavoriteUseCase {
    override suspend fun run(wordId: Int) {
        repository.removeFavorite(wordId)
    }
}
