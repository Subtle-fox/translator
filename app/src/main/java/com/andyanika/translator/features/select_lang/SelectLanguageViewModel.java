package com.andyanika.translator.features.select_lang;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.di.FragmentScope;
import com.andyanika.usecases.HistoryUseCase;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

@FragmentScope
public class SelectLanguageViewModel extends ViewModel {
    private Disposable disposable;

    public MutableLiveData<List<LanguageCode>> data = new MutableLiveData<>();

    @Inject
    SelectLanguageViewModel(HistoryUseCase historyUseCase) {
        disposable = Observable.just(Arrays.asList(LanguageCode.values())).subscribe(data::postValue);
    }

    @Override
    protected void onCleared() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onCleared();
    }
}
