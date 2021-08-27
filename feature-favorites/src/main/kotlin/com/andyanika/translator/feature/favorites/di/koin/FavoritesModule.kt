package com.andyanika.translator.feature.favorites.di.koin

import com.andyanika.translator.feature.favorites.FavoriteFragment
import com.andyanika.translator.feature.favorites.FavoritesListAdapter
import com.andyanika.translator.feature.favorites.FavoritesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoritesModule = module {
    scope<FavoriteFragment> {
        scoped { params -> FavoritesListAdapter(params.get()) }
    }

    viewModel<FavoritesViewModel>()
}
