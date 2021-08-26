package com.andyanika.translator.feature.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andyanika.translator.common.interfaces.usecase.GetFavoritesUseCase
import com.andyanika.translator.common.interfaces.usecase.RemoveFavoriteUseCase
import com.andyanika.translator.common.models.FavoriteModel
import com.andyanika.translator.common.scopes.FragmentScope
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class FavoritesViewModel @Inject internal constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
) : ViewModel() {
    val data = MutableLiveData<List<FavoriteModel>>()
    private var listDisposable: Disposable? = null
    private var itemClickDisposable: Disposable? = null
    fun load(limit: Int) {
        if (listDisposable == null || listDisposable!!.isDisposed) {
            listDisposable =
                getFavoritesUseCase.run(limit).subscribe { value: List<FavoriteModel> -> data.postValue(value) }
        }
    }

    fun subscribeItemClick(observable: Observable<FavoriteModel>) {
        itemClickDisposable = observable
            .flatMapCompletable { model: FavoriteModel? ->
                removeFavoriteUseCase.run(
                    model!!.id
                )
                    .doOnComplete { Timber.d("favorite removed") }
            }
            .subscribe()
    }

    fun unsubscribeItemClick() {
        if (itemClickDisposable != null && !itemClickDisposable!!.isDisposed) {
            itemClickDisposable!!.dispose()
        }
    }

    override fun onCleared() {
        unsubscribeItemClick()
        if (!listDisposable!!.isDisposed) {
            listDisposable!!.dispose()
        }
        super.onCleared()
    }
}
