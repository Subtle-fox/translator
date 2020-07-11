package com.andyanika.translator.feature.favorites;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.andyanika.translator.common.interfaces.usecase.GetFavoritesUseCase;
import com.andyanika.translator.common.interfaces.usecase.RemoveFavoriteUseCase;
import com.andyanika.translator.common.models.FavoriteModel;
import com.andyanika.translator.common.scopes.FragmentScope;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import timber.log.Timber;

@FragmentScope
public class FavoritesViewModel extends ViewModel {
    final MutableLiveData<List<FavoriteModel>> data = new MutableLiveData<>();

    private final GetFavoritesUseCase getFavoritesUseCase;
    private final RemoveFavoriteUseCase removeFavoriteUseCase;
    private Disposable listDisposable;
    private Disposable itemClickDisposable;

    @Inject
    FavoritesViewModel(GetFavoritesUseCase getFavoritesUseCase, RemoveFavoriteUseCase removeFavoriteUseCase) {
        this.getFavoritesUseCase = getFavoritesUseCase;
        this.removeFavoriteUseCase = removeFavoriteUseCase;
    }

    void load(int limit) {
        if (listDisposable == null || listDisposable.isDisposed()) {
            listDisposable = getFavoritesUseCase.run(limit).subscribe(data::postValue);
        }
    }

    void subscribeItemClick(Observable<FavoriteModel> observable) {
        itemClickDisposable = observable
                .flatMapCompletable(model -> removeFavoriteUseCase.run(model.id)
                        .doOnComplete(() -> Timber.d("favorite removed")))
                .subscribe();
    }

    void unsubscribeItemClick() {
        if (itemClickDisposable != null && !itemClickDisposable.isDisposed()) {
            itemClickDisposable.dispose();
        }
    }

    @Override
    protected void onCleared() {
        unsubscribeItemClick();
        if (!listDisposable.isDisposed()) {
            listDisposable.dispose();
        }
        super.onCleared();
    }
}
