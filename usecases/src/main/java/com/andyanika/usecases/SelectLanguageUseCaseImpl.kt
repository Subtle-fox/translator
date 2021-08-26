package com.andyanika.usecases

import com.andyanika.translator.common.interfaces.LocalRepository
import com.andyanika.translator.common.interfaces.usecase.SelectLanguageUseCase
import com.andyanika.translator.common.models.LanguageCode
import com.andyanika.translator.common.models.TranslateDirection
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SelectLanguageUseCaseImpl @Inject constructor(
    private val repository: LocalRepository,
    private val ioScheduler: Scheduler,
    private val ioDispatcher: CoroutineDispatcher,
) : SelectLanguageUseCase {
    override fun setSrc(code: LanguageCode): Completable {
        return Completable.fromObservable(
            repository.srcLanguage
                .subscribeOn(ioScheduler)
                .zipWith(repository.dstLanguage, { src: LanguageCode?, dst: LanguageCode? ->
                    TranslateDirection(
                        src!!, dst!!
                    )
                })
                .take(1)
                .map { oldDirection: TranslateDirection<LanguageCode> ->
                    normalize(
                        TranslateDirection(
                            code,
                            oldDirection.dst
                        ), oldDirection
                    )
                }
                .doOnNext { direction: TranslateDirection<LanguageCode?>? -> repository.setLanguageDirection(direction) })
    }

    override fun setDst(code: LanguageCode): Completable {
        return Completable.fromObservable(
            repository.srcLanguage
                .subscribeOn(ioScheduler)
                .zipWith(repository.dstLanguage, { src: LanguageCode?, dst: LanguageCode? ->
                    TranslateDirection(
                        src!!, dst!!
                    )
                })
                .take(1)
                .map { oldDirection: TranslateDirection<LanguageCode> ->
                    normalize(
                        TranslateDirection(
                            oldDirection.src,
                            code
                        ), oldDirection
                    )
                }
                .doOnNext { direction: TranslateDirection<LanguageCode?>? -> repository.setLanguageDirection(direction) })
    }

    override suspend fun swap() {
        return withContext(ioDispatcher) {
            repository.srcLanguage
                .subscribeOn(ioScheduler)
                .zipWith(
                    repository.dstLanguage,
                    { src: LanguageCode, dst: LanguageCode -> TranslateDirection(dst, src) })
                .take(1)
                .doOnNext { direction: TranslateDirection<LanguageCode>? ->
                    repository.setLanguageDirection(
                        direction
                    )
                }
                .blockingFirst()
        }
    }

    fun normalize(
        newDirection: TranslateDirection<LanguageCode>,
        oldDirection: TranslateDirection<LanguageCode>
    ): TranslateDirection<LanguageCode?> {
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
