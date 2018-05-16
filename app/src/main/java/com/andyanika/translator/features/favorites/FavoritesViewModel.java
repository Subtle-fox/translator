package com.andyanika.translator.features.favorites;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.andyanika.translator.common.models.TranslationRowModel;
import com.andyanika.translator.di.FragmentScope;
import com.andyanika.usecases.GetFavoritesUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

@FragmentScope
public class FavoritesViewModel extends ViewModel {
    private Disposable disposable;
    public MutableLiveData<List<TranslationRowModel>> data = new MutableLiveData<>();

    @Inject
    FavoritesViewModel(GetFavoritesUseCase getFavoritesUseCase) {
        disposable = getFavoritesUseCase.run(null).subscribe(data::postValue);
    }

    @Override
    protected void onCleared() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onCleared();
    }
}
