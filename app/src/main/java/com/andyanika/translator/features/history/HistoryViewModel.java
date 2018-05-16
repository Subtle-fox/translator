package com.andyanika.translator.features.history;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRowModel;
import com.andyanika.translator.di.FragmentScope;
import com.andyanika.usecases.HistoryUseCase;

import javax.inject.Inject;
import java.util.List;

import io.reactivex.disposables.Disposable;

@FragmentScope
public class HistoryViewModel extends ViewModel {
    private Disposable disposable;

    public MutableLiveData<List<TranslationRowModel>> data = new MutableLiveData<>();

    @Inject
    HistoryViewModel(HistoryUseCase historyUseCase) {
        disposable = historyUseCase.run(null).subscribe(data::postValue);
    }

    public void load() {
        filter(null);
    }

    public void filter(final String filter) {
        // TODO: 16.05.2018
    }

    @Override
    protected void onCleared() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onCleared();
    }
}
