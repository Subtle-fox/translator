package com.andyanika.translator.feature.favorites;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.andyanika.resources.di.FragmentScope;
import com.andyanika.translator.common.interfaces.usecase.GetFavoritesUseCase;
import com.andyanika.translator.common.interfaces.usecase.RemoveFavoriteUseCase;
import com.andyanika.translator.common.models.UiTranslationModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

@FragmentScope
public class FavoritesViewModel extends ViewModel {
    final MutableLiveData<List<UiTranslationModel>> data = new MutableLiveData<>();

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

    void subscribeItemClick(Observable<UiTranslationModel> observable) {
        itemClickDisposable = observable
                .flatMapCompletable(model -> removeFavoriteUseCase.run(model.id))
                .subscribe(() -> System.out.println("favorite removed"));
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
