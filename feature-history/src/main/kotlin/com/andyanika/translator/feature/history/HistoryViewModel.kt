package com.andyanika.translator.feature.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.interfaces.usecase.AddFavoriteUseCase
import core.interfaces.usecase.HistoryUseCase
import core.interfaces.usecase.RemoveFavoriteUseCase
import core.models.FavoriteModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class HistoryViewModel(
    private val historyUseCase: HistoryUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
) : ViewModel() {

    private val _data: MutableLiveData<List<FavoriteModel>> = MutableLiveData<List<FavoriteModel>>()
    val data: LiveData<List<FavoriteModel>> = _data

    val showClearBtn = MutableLiveData<Boolean>()

    fun subscribeSearch(searchTextFlow: Flow<String>, limit: Int) {
        viewModelScope.launch {
            searchTextFlow
                .distinctUntilChanged()
                .debounce(300L)
                .flatMapLatest { str -> historyUseCase.run(str, limit) }
                .collect { _data.value = it }
        }
    }

    fun subscribeItemClick(model: FavoriteModel) {
        viewModelScope.launch {
            if (model.isFavorite) {
                removeFavoriteUseCase.run(model.id)
                Timber.d("favorite removed")
            } else {
                addFavoriteUseCase.run(model.id)
                Timber.d("favorite added")
            }
        }
    }

    init {
        showClearBtn.value = false
    }
}
