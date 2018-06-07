package com.andyanika.translator.feature.select_lang;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.andyanika.resources.Extras;
import com.andyanika.resources.Screens;
import com.andyanika.resources.di.FragmentScope;
import com.andyanika.translator.common.interfaces.usecase.GetLanguagesUseCase;
import com.andyanika.translator.common.interfaces.usecase.SelectLanguageUseCase;
import com.andyanika.translator.common.models.UiLanguageModel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;

@FragmentScope
public class SelectLanguageViewModel extends ViewModel {
    MutableLiveData<List<UiLanguageModel>> data = new MutableLiveData<>();

    private final GetLanguagesUseCase getLanguagesUseCase;
    private final SelectLanguageUseCase selectLanguageUseCase;
    private final Scheduler uiScheduler;
    private final Router router;
    private Disposable listDisposable;
    private Disposable itemClickDisposable;
    private boolean isSrcMode;

    @Inject
    SelectLanguageViewModel(GetLanguagesUseCase getLanguagesUseCase, SelectLanguageUseCase selectLanguageUseCase, Router router, @Named("ui") Scheduler uiScheduler) {
        this.router = router;
        this.getLanguagesUseCase = getLanguagesUseCase;
        this.selectLanguageUseCase = selectLanguageUseCase;
        this.uiScheduler = uiScheduler;
    }

    void setMode(String mode) {
        isSrcMode = Extras.MODE_SRC.equals(mode);
    }

    public void loadData() {
        listDisposable = getLanguagesUseCase.run(isSrcMode).subscribe(data::postValue);
    }

    public void subscribeItemClick(Single<UiLanguageModel> single) {
        itemClickDisposable = single
                .flatMapCompletable(model -> {
                    System.out.println("click received, mode " + isSrcMode);
                    if (isSrcMode) {
                        return selectLanguageUseCase.setSrc(model.code);
                    } else {
                        return selectLanguageUseCase.setDst(model.code);
                    }
                })
                .observeOn(uiScheduler)
                .subscribe(() -> {
                    System.out.println("subscrived -> navigate back");
                    router.backTo(Screens.TRANSLATION);
                });
    }

    public void unsubscribeItemClick() {
        if (itemClickDisposable != null && !itemClickDisposable.isDisposed()) {
            itemClickDisposable.dispose();
        }
    }

    @Override
    protected void onCleared() {
        unsubscribeItemClick();
        if (listDisposable != null && !listDisposable.isDisposed()) {
            listDisposable.dispose();
        }
        super.onCleared();
    }
}
