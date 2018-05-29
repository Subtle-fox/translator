package com.andyanika.translator.features.select_lang;

import com.andyanika.translator.common.models.LanguageRowModel;
import com.andyanika.translator.di.FragmentScope;
import com.andyanika.translator.ui.Screens;
import com.andyanika.usecases.SelectLanguageUseCase;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;

@FragmentScope
public class SelectLanguagePresenter {
    private final SelectLanguageUseCase selectLanguageUseCase;
    private final Router router;
    private boolean isSrcMode;
    private Disposable disposable;

    @Inject
    SelectLanguagePresenter(SelectLanguageUseCase selectLanguageUseCase, Router router) {
        this.selectLanguageUseCase = selectLanguageUseCase;
        this.router = router;
    }

    void setMode(String mode) {
        isSrcMode = Extras.MODE_SRC.equals(mode);
    }

    boolean isSrcMode() {
        return isSrcMode;
    }

    void dispose() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public void subscribe(Single<LanguageRowModel> single) {
        disposable = single.flatMapCompletable(model -> {
            if (isSrcMode) {
                return selectLanguageUseCase.setSrc(model.code);
            } else {
                return selectLanguageUseCase.setDst(model.code);
            }
        }).subscribe(() -> router.backTo(Screens.TRANSLATION));
    }
}