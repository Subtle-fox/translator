package com.andyanika.usecases

import com.andyanika.translator.common.interfaces.LocalRepository
import com.andyanika.translator.common.interfaces.RemoteRepository
import com.andyanika.translator.common.interfaces.usecase.TranslationUseCase
import com.andyanika.translator.common.models.LanguageCode
import com.andyanika.translator.common.models.TranslateDirection
import com.andyanika.translator.common.models.TranslateRequest
import com.andyanika.translator.common.models.TranslateResult
import com.andyanika.translator.common.models.ui.DisplayTranslateResult
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TranslateUseCaseImpl @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository,
    private val dispatcher: CoroutineDispatcher
) : TranslationUseCase {

    override suspend fun run(srcText: String): DisplayTranslateResult {
        return withContext(dispatcher) {
            val request = Observable
                .zip(localRepository.srcLanguage, localRepository.dstLanguage, ::TranslateDirection)
                .map { direction -> TranslateRequest(srcText, direction) }
                .blockingFirst()

            try {
                translateLocally(request)
            } catch (_: Throwable) {
                translateRemotely(request)
            }
        }
    }

    fun getTranslationRequest(srcText: String): Observable<TranslateRequest> {
        return Observable
            .zip(localRepository.srcLanguage, localRepository.dstLanguage, { src, dst -> TranslateDirection(src, dst) })
            .timeout(500, TimeUnit.MILLISECONDS)
            .take(1)
            .map { direction -> TranslateRequest(srcText, direction) }
            .doOnNext { (text) -> Timber.d("translation request: %s", text) }
    }

    fun translateLocally(request: TranslateRequest?): DisplayTranslateResult {
        val result = localRepository
            .translate(request)
            .blockingGet()

        Timber.d("local next: %s", result)
        return DisplayTranslateResult(result, true)
    }

    suspend fun translateRemotely(request: TranslateRequest): DisplayTranslateResult {
        return try {
            remoteRepository
                .translate(request)
                ?.let { result ->
                    Timber.d("remote next: %s", result)
                    val localResult = addToLocalRepo(result)
                    DisplayTranslateResult(localResult, false)
                }
                ?: DisplayTranslateResult.createEmptyResult(request, false)
        } catch (t: Throwable) {
            Timber.e(t, "remote error")
            DisplayTranslateResult.createEmptyResult(request, true)
        }
    }

    fun addToLocalRepo(result: TranslateResult): TranslateResult {
        return localRepository.addTranslation(result)
            .onErrorReturnItem(result)
            .blockingGet()
    }
}
