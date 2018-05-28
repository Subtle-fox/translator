package com.andyanika.translator.features.select_lang;

import com.andyanika.translator.common.models.LanguageRowModel;
import com.andyanika.translator.di.FragmentScope;
import com.andyanika.translator.ui.Callback;
import com.andyanika.translator.ui.Screens;
import com.andyanika.usecases.SelectLanguageUseCase;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.terrakok.cicerone.Router;

@FragmentScope
public class SelectLanguagePresenter implements Callback<LanguageRowModel> {
    private final SelectLanguageUseCase selectLanguageUseCase;
    private final Router router;
    private boolean consumed;
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

    @Override
    public void onClick(LanguageRowModel model) {
        if (!consumed) {
            consumed = true;
            Completable completable;
            if (isSrcMode) {
                completable = selectLanguageUseCase.setSrc(model.code);
            } else {
                completable = selectLanguageUseCase.setDst(model.code);
            }
            disposable = completable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> router.backTo(Screens.TRANSLATION));
        }
    }

    void dispose() {
        if (disposable != null && disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}