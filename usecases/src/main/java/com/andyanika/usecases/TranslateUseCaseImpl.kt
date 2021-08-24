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
import io.reactivex.rxjava3.core.Scheduler
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

class TranslateUseCaseImpl @Inject constructor(
    private val localRepository: LocalRepository,
    @param:Named("yandex") private val remoteRepository: RemoteRepository,
    @param:Named("io") private val ioScheduler: Scheduler
) : TranslationUseCase {
    override suspend fun run(srcText: String?): DisplayTranslateResult? {
        return srcText?.let {
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

//        return if (srcText!!.isEmpty()) {
//            Observable.empty()
//        } else getTranslationRequest(srcText)
//            .flatMap { request: TranslateRequest? ->
//                translateLocally(request)
//                    .onErrorResumeWith(translateRemotely(request))
//            }
//            .subscribeOn(ioScheduler)
    }

    fun getTranslationRequest(srcText: String?): Observable<TranslateRequest> {
        return Observable
            .zip(localRepository.srcLanguage, localRepository.dstLanguage, { src: LanguageCode?, dst: LanguageCode? ->
                TranslateDirection(
                    src!!, dst!!
                )
            })
            .timeout(500, TimeUnit.MILLISECONDS)
            .take(1)
            .map { direction: TranslateDirection<LanguageCode>? ->
                TranslateRequest(
                    srcText!!, direction!!
                )
            }
            .doOnNext { (text) -> Timber.d("translation request: %s", text) }
    }

    suspend fun translateLocally(request: TranslateRequest?): DisplayTranslateResult {
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

    suspend fun addToLocalRepo(result: TranslateResult): TranslateResult {
        return localRepository.addTranslation(result)
            .onErrorReturnItem(result)
            .blockingGet()
    }
}
