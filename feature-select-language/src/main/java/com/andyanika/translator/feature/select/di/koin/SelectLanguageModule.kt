package com.andyanika.translator.feature.select.di.koin

import com.andyanika.translator.feature.select.SelectLanguageFragment
import com.andyanika.translator.feature.select.SelectLanguageListAdapter
import com.andyanika.translator.feature.select.SelectLanguageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val selectLanguageModule = module {

    scope<SelectLanguageFragment> {
        scoped { params -> SelectLanguageListAdapter(params.get()) }
    }

    viewModel {
        SelectLanguageViewModel(get(), get(), get())
    }
}
