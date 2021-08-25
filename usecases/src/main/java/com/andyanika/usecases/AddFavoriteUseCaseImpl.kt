package com.andyanika.usecases

import com.andyanika.translator.common.interfaces.LocalRepository
import com.andyanika.translator.common.interfaces.usecase.AddFavoriteUseCase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject
import javax.inject.Named

class AddFavoriteUseCaseImpl @Inject constructor(
    private val repository: LocalRepository,
    @Named("io") private val ioScheduler: Scheduler
) : AddFavoriteUseCase {
    override fun run(wordId: Int): Completable {
        return Completable
            .fromRunnable { repository.addFavorites(wordId) }
            .subscribeOn(ioScheduler)
    }
}
