package com.andyanika.translator.features.favorites;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRowModel;
import com.andyanika.translator.di.FragmentScope;
import com.andyanika.usecases.GetFavoritesUseCase;

import javax.inject.Inject;
import java.util.List;

@FragmentScope
public class FavoritesViewModel extends ViewModel {
    private GetFavoritesUseCase getFavoritesUseCase;

    public MutableLiveData<List<TranslationRowModel>> data = new MutableLiveData<>();

    @Inject
    FavoritesViewModel(GetFavoritesUseCase getFavoritesUseCase) {
        this.getFavoritesUseCase = getFavoritesUseCase;
    }

    public void load() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<TranslationRowModel> favorites = getFavoritesUseCase.run(null);
                data.postValue(favorites);
            }
        }).start();
    }
}
