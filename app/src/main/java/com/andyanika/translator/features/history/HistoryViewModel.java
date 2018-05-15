package com.andyanika.translator.features.history;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRowModel;
import com.andyanika.translator.di.FragmentScope;
import com.andyanika.usecases.HistoryUseCase;

import javax.inject.Inject;
import java.util.List;

@FragmentScope
public class HistoryViewModel extends ViewModel {
    private HistoryUseCase historyUseCase;

    public MutableLiveData<List<TranslationRowModel>> data = new MutableLiveData<>();

    @Inject
    HistoryViewModel(HistoryUseCase historyUseCase) {
        this.historyUseCase = historyUseCase;
    }

    public void load() {
        filter(null);
    }

    public void filter(final String filter) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<TranslationRowModel> filtered = historyUseCase.run(filter);
                data.postValue(filtered);
            }
        }).start();
    }
}
