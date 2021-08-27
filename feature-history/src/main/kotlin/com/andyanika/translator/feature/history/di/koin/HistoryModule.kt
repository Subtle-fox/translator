package com.andyanika.translator.feature.history.di.koin

import com.andyanika.translator.feature.history.HistoryFragment
import com.andyanika.translator.feature.history.HistoryListAdapter
import com.andyanika.translator.feature.history.HistoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val historyModule = module {
    scope<HistoryFragment> {
        scoped { params -> HistoryListAdapter(params.get()) }
    }

    viewModel<HistoryViewModel>()
}
