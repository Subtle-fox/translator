package com.andyanika.translator.features.favorites;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.andyanika.translator.common.models.TranslationRowModel;
import com.andyanika.translator.di.FragmentScope;
import com.andyanika.usecases.GetFavoritesUseCase;
import com.andyanika.usecases.RemoveFavoriteUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

@FragmentScope
public class FavoritesViewModel extends ViewModel {
    final MutableLiveData<List<TranslationRowModel>> data = new MutableLiveData<>();

    private final GetFavoritesUseCase getFavoritesUseCase;
    private final RemoveFavoriteUseCase removeFavoriteUseCase;
    private Disposable listDisposable;
    private Disposable itemClickDisposable;

    @Inject
    FavoritesViewModel(GetFavoritesUseCase getFavoritesUseCase, RemoveFavoriteUseCase removeFavoriteUseCase) {
        this.getFavoritesUseCase = getFavoritesUseCase;
        this.removeFavoriteUseCase = removeFavoriteUseCase;
    }

    void load() {
        if (listDisposable == null || listDisposable.isDisposed()) {
            listDisposable = getFavoritesUseCase.run().subscribe(data::postValue);
        }
    }

    void subscribeItemClick(Observable<TranslationRowModel> observable) {
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
