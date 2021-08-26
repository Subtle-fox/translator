package com.andyanika.usecases

import com.andyanika.translator.common.interfaces.LocalRepository
import com.andyanika.translator.common.interfaces.usecase.AddFavoriteUseCase

internal class AddFavoriteUseCaseImpl(
    private val repository: LocalRepository,
) : AddFavoriteUseCase {

    override suspend fun run(wordId: Int) {
        repository.addFavorites(wordId)
    }
}
