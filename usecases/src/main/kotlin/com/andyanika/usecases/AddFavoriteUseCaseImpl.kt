package com.andyanika.usecases

import core.interfaces.LocalRepository
import core.interfaces.usecase.AddFavoriteUseCase

internal class AddFavoriteUseCaseImpl(
    private val repository: LocalRepository,
) : AddFavoriteUseCase {

    override suspend fun run(wordId: Int) {
        repository.addFavorites(wordId)
    }
}
