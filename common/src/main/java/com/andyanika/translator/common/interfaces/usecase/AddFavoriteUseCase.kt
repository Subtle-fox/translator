package com.andyanika.translator.common.interfaces.usecase

interface AddFavoriteUseCase {
    suspend fun run(wordId: Int)
}
