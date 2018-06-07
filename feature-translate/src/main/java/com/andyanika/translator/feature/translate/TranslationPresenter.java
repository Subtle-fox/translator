package com.andyanika.translator.feature.translate;


import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.andyanika.resources.di.FragmentScope;
import com.andyanika.translator.common.interfaces.usecase.GetSelectedLanguageUseCase;
import com.andyanika.translator.common.interfaces.usecase.SelectLanguageUseCase;
import com.andyanika.translator.common.interfaces.usecase.TranslationUseCase;
import com.andyanika.translator.common.models.TranslateResult;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

@FragmentScope
public class TranslationPresenter {
    private static final long DELAY = 1;

    private final TranslationView view;
    private TranslationUseCase translateUseCase;
    private SelectLanguageUseCase selectLanguageUseCase;
    private GetSelectedLanguageUseCase getSelectedLanguagesUseCase;
    private Scheduler uiScheduler;

    private Disposable searchDisposable;
    private Disposable languageDisposable;
    private Disposable swapDisposable;

    private PublishSubject<String> retrySubject;

    @Inject
    TranslationPresenter(TranslationView view,
                         TranslationUseCase translateUseCase,
                         GetSelectedLanguageUseCase getSelectedLanguagesUseCase,
                         SelectLanguageUseCase selectLanguageUseCase,
                         PublishSubject<String> retrySubject,
                         @Named("ui") Scheduler uiScheduler) {
        this.view = view;
        this.translateUseCase = translateUseCase;
        this.getSelectedLanguagesUseCase = getSelectedLanguagesUseCase;
        this.selectLanguageUseCase = selectLanguageUseCase;
        this.uiScheduler = uiScheduler;
        this.retrySubject = retrySubject;
    }

    public void translate(@NonNull final String text) {
        retrySubject.onNext(text);
    }

    public void clear() {
        view.hideClearBtn();
        view.hideErrorLayout();
        view.clearResult();
    }

    private void showProgress(CharSequence charSequence) {
        uiScheduler.scheduleDirect(() -> {
            view.hideErrorLayout();
            view.hideOffline();
            view.hideClearBtn();

            if (TextUtils.isEmpty(charSequence)) {
                view.hideProgress();
                view.clearTranslation();
            } else {
                view.showProgress();
            }
        });
    }


    public void subscribe(Observable<CharSequence> searchTextObservable) {
        languageDisposable = getSelectedLanguagesUseCase
                .run()
                .observeOn(uiScheduler)
                .subscribe(pair -> {
                    view.setSrcLabel(pair.src);
                    view.setDstLabel(pair.dst);
                });

        searchDisposable = searchTextObservable
                .distinctUntilChanged(CharSequence::equals)
                .map(CharSequence::toString)
                .distinctUntilChanged(String::equals)
                .mergeWith(retrySubject)
                .doOnNext(this::showProgress)
                .debounce(DELAY, TimeUnit.SECONDS)
                .switchMap(str -> translateUseCase.run(str))
                .observeOn(uiScheduler)
                .subscribe(this::processResult, this::processError);
    }

    private void processError(Throwable throwable) {
        throwable.printStackTrace();

        view.showErrorLayout();
        view.clearTranslation();
        view.hideProgress();
        view.showClearBtn();
        view.hideOffline();
    }

    private void processResult(TranslateResult result) {
        System.out.println("process result: " + result.textTranslated);
        if (result.isFound) {
            processFoundResult(result);
        } else {
            processEmptyResult(result);
        }
    }

    private void processFoundResult(TranslateResult result) {
        view.showTranslation(result);
        view.hideProgress();
        view.showClearBtn();

        if (result.isOffline) {
            view.showOffline();
        } else {
            view.hideOffline();
        }
    }

    private void processEmptyResult(TranslateResult result) {
        if (result.isError) {
            view.showErrorLayout();
            view.clearTranslation();
        } else {
            view.showTranslation(result);
        }

        view.hideProgress();
        view.showClearBtn();
        view.hideOffline();
    }

    public void swapDirection() {
        swapDisposable = selectLanguageUseCase
                .swap()
                .observeOn(uiScheduler)
                .subscribe();
    }

    void dispose() {
        dispose(searchDisposable);
        dispose(languageDisposable);
        dispose(swapDisposable);
    }

    private void dispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
