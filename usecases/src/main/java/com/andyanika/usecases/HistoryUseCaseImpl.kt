package com.andyanika.usecases

import com.andyanika.translator.common.interfaces.LocalRepository
import com.andyanika.translator.common.interfaces.usecase.HistoryUseCase
import com.andyanika.translator.common.models.FavoriteModel
import com.andyanika.translator.common.models.TranslateResult
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject
import javax.inject.Named

class HistoryUseCaseImpl @Inject constructor(
    private val repository: LocalRepository,
    @Named("io") private val ioScheduler: Scheduler
) : HistoryUseCase {
    override fun run(filter: String, limit: Int): Flowable<List<FavoriteModel>> {
        return repository
            .history
            .subscribeOn(ioScheduler)
            .take(limit.toLong())
            .flatMap { list: List<FavoriteModel>? ->
                Flowable.fromIterable(list)
                    .filter { item: FavoriteModel -> filter(item.translateResult, filter) }.toList()
                    .toFlowable()
            }
    }

    private fun filter(result: TranslateResult, filter: String?): Boolean {
        // TODO: 31.05.2018: implement via Room query
        return (filter == null || filter.isEmpty()
            || result.textSrc.contains(filter)
            || result.textDst.contains(filter))
    }
}
