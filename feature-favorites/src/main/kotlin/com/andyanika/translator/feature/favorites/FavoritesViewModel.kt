package com.andyanika.translator.feature.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.interfaces.usecase.GetFavoritesUseCase
import core.interfaces.usecase.RemoveFavoriteUseCase
import core.models.FavoriteModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
) : ViewModel() {

    private val _data = MutableLiveData<List<FavoriteModel>>()
    val data: LiveData<List<FavoriteModel>> = _data

    fun load() {
        viewModelScope.launch {
            getFavoritesUseCase
                .run()
                .collect(_data::setValue)
        }
    }

    fun removeFavorite(model: FavoriteModel) {
        viewModelScope.launch {
            removeFavoriteUseCase.run(model.id)
        }
    }
}
