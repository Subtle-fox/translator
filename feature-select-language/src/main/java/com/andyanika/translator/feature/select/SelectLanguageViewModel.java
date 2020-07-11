package com.andyanika.translator.feature.select;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andyanika.translator.common.constants.Extras;
import com.andyanika.translator.common.constants.Screens;
import com.andyanika.translator.common.interfaces.ScreenRouter;
import com.andyanika.translator.common.interfaces.usecase.GetLanguagesUseCase;
import com.andyanika.translator.common.interfaces.usecase.SelectLanguageUseCase;
import com.andyanika.translator.common.models.ui.DisplayLanguageModel;
import com.andyanika.translator.common.scopes.FragmentScope;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import timber.log.Timber;

@FragmentScope
public class SelectLanguageViewModel extends ViewModel {
    private final GetLanguagesUseCase getLanguagesUseCase;
    private final SelectLanguageUseCase selectLanguageUseCase;
    private final Scheduler uiScheduler;
    private final ScreenRouter router;
    MutableLiveData<List<DisplayLanguageModel>> data = new MutableLiveData<>();
    private Disposable listDisposable;
    private Disposable itemClickDisposable;
    private boolean isSrcMode;

    @Inject
    SelectLanguageViewModel(GetLanguagesUseCase getLanguagesUseCase, SelectLanguageUseCase selectLanguageUseCase, ScreenRouter router, @Named("ui") Scheduler uiScheduler) {
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

    public void subscribeItemClick(Single<DisplayLanguageModel> single) {
        itemClickDisposable = single
                .flatMapCompletable(model -> {
                    Timber.d("select language -> click received (isSrcMode = %b)", isSrcMode);
                    if (isSrcMode) {
                        return selectLanguageUseCase.setSrc(model.code);
                    } else {
                        return selectLanguageUseCase.setDst(model.code);
                    }
                })
                .observeOn(uiScheduler)
                .doOnComplete(() -> Timber.d("navigate back"))
                .subscribe(() -> router.backTo(Screens.TRANSLATION));
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
