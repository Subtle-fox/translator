package com.andyanika.usecases

import com.andyanika.translator.common.interfaces.LocalRepository
import com.andyanika.translator.common.interfaces.usecase.RemoveFavoriteUseCase

internal class RemoveFavoriteUseCaseImpl(
    private val repository: LocalRepository,
) : RemoveFavoriteUseCase {
    override suspend fun run(wordId: Int) {
        repository.removeFavorite(wordId)
    }
}
