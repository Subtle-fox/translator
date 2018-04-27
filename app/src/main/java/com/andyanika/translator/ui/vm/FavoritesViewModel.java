package com.andyanika.translator.ui.vm;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.di.FragmentScope;
import com.andyanika.usecases.FavoritesUseCase;

import javax.inject.Inject;
import java.util.List;

@FragmentScope
public class FavoritesViewModel extends ViewModel {
    private FavoritesUseCase favoritesUseCase;

    public MutableLiveData<List<TranslateResult>> data = new MutableLiveData<>();

    @Inject
    FavoritesViewModel(FavoritesUseCase favoritesUseCase) {
        this.favoritesUseCase = favoritesUseCase;
    }

    public void load() {
        this.data.setValue(favoritesUseCase.run(null));
    }
}
