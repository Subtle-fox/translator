package com.andyanika.translator.common.interfaces.usecase

interface RemoveFavoriteUseCase {
    suspend fun run(wordId: Int)
}
