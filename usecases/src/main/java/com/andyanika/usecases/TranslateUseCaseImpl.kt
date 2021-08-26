package com.andyanika.usecases

import com.andyanika.translator.common.interfaces.LocalRepository
import com.andyanika.translator.common.interfaces.RemoteRepository
import com.andyanika.translator.common.interfaces.usecase.TranslationUseCase
import com.andyanika.translator.common.models.TranslateDirection
import com.andyanika.translator.common.models.TranslateRequest
import com.andyanika.translator.common.models.ui.DisplayTranslateResult
import timber.log.Timber

class TranslateUseCaseImpl(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository,
) : TranslationUseCase {

    override suspend fun run(srcText: String): DisplayTranslateResult {
        val request = getTranslationRequest(srcText)
        return translateLocally(request) ?: translateRemotely(request)
    }

    suspend fun getTranslationRequest(srcText: String): TranslateRequest {
        val direction = TranslateDirection(
            localRepository.getSrcLanguage(),
            localRepository.getDstLanguage()
        )
        return TranslateRequest(srcText, direction)
    }

    suspend fun translateLocally(request: TranslateRequest): DisplayTranslateResult? {
        return localRepository
            .translate(request)
            ?.let { result -> DisplayTranslateResult(result, true) }
    }

    suspend fun translateRemotely(request: TranslateRequest): DisplayTranslateResult {
        return try {
            remoteRepository.translate(request)
                ?.let { result ->
                    localRepository.addTranslation(result)
                    DisplayTranslateResult(result, false)
                }
                ?: DisplayTranslateResult.createEmptyResult(request, false)
        } catch (t: Throwable) {
            Timber.e(t, "remote error")
            DisplayTranslateResult.createEmptyResult(request, true)
        }
    }
}
