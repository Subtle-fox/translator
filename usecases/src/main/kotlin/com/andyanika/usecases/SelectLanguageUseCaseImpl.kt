package com.andyanika.usecases

import core.interfaces.LocalRepository
import core.interfaces.usecase.SelectLanguageUseCase
import core.models.LanguageCode
import core.models.TranslateDirection

internal class SelectLanguageUseCaseImpl(
    private val repository: LocalRepository,
) : SelectLanguageUseCase {

    override suspend fun setSrc(code: LanguageCode) {
        val oldDirection = TranslateDirection(
            repository.getSrcLanguage(),
            repository.getDstLanguage()
        )

        val newDirection = normalize(
            newDirection = TranslateDirection(src = code, dst = oldDirection.dst),
            oldDirection = oldDirection
        )

        repository.setLanguageDirection(newDirection)
    }

    override suspend fun setDst(code: LanguageCode) {
        val oldDirection = TranslateDirection(
            repository.getSrcLanguage(),
            repository.getDstLanguage()
        )

        val newDirection = normalize(
            newDirection = TranslateDirection(src = oldDirection.src, dst = code),
            oldDirection = oldDirection
        )

        repository.setLanguageDirection(newDirection)
    }

    override suspend fun swap() {
        val newDirection = TranslateDirection(
            src = repository.getDstLanguage(),
            dst = repository.getSrcLanguage()
        )

        repository.setLanguageDirection(newDirection)
    }

    private fun normalize(
        newDirection: TranslateDirection<LanguageCode>,
        oldDirection: TranslateDirection<LanguageCode>
    ): TranslateDirection<LanguageCode> {
        if (newDirection.src == oldDirection.dst) {
            // swap
            return TranslateDirection(newDirection.src, oldDirection.src)
        } else if (newDirection.dst == oldDirection.src) {
            // swap
            return TranslateDirection(oldDirection.dst, newDirection.src)
        }
        return TranslateDirection(newDirection.src, newDirection.dst)
    }
}
