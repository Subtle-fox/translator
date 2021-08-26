package com.andyanika.usecases

import com.andyanika.translator.common.interfaces.LocalRepository
import com.andyanika.translator.common.interfaces.usecase.HistoryUseCase
import com.andyanika.translator.common.models.FavoriteModel
import com.andyanika.translator.common.models.TranslateResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class HistoryUseCaseImpl(
    private val repository: LocalRepository,
) : HistoryUseCase {
    override fun run(filterStr: String, limit: Int): Flow<List<FavoriteModel>> {
        return repository.getHistory()
            .map { allHistory ->
                allHistory
                    .filter { filter(it.translateResult, filterStr) }
                    .subList(0, limit)
            }
    }

    private fun filter(result: TranslateResult, filter: String?): Boolean {
        // TODO: 31.05.2018: implement via Room query
        return (filter == null || filter.isEmpty()
            || result.textSrc.contains(filter)
            || result.textDst.contains(filter))
    }
}
