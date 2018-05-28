package com.andyanika.translator.features.select_lang;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.andyanika.translator.common.models.LanguageRowModel;
import com.andyanika.translator.di.FragmentScope;
import com.andyanika.usecases.GetLanguagesUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

@FragmentScope
public class SelectLanguageViewModel extends ViewModel {
    private Disposable disposable;

    public MutableLiveData<List<LanguageRowModel>> data = new MutableLiveData<>();
    private GetLanguagesUseCase useCase;

    @Inject
    SelectLanguageViewModel(GetLanguagesUseCase useCase) {
        this.useCase = useCase;
    }

    public void load(boolean isSrcMode) {
        disposable = useCase.run(isSrcMode).subscribe(data::postValue);
    }

    @Override
    protected void onCleared() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onCleared();
    }
}
