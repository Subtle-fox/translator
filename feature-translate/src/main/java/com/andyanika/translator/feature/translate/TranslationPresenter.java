package com.andyanika.translator.feature.translate;


import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.andyanika.translator.common.interfaces.usecase.GetSelectedLanguageUseCase;
import com.andyanika.translator.common.interfaces.usecase.SelectLanguageUseCase;
import com.andyanika.translator.common.interfaces.usecase.TranslationUseCase;
import com.andyanika.translator.common.models.ui.DisplayTranslateResult;
import com.andyanika.translator.common.scopes.FragmentScope;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import timber.log.Timber;

@FragmentScope
public class TranslationPresenter {
    static final long DELAY = 1;

    private final TranslationView view;
    private final TranslationUseCase translateUseCase;
    private final SelectLanguageUseCase selectLanguageUseCase;
    private final GetSelectedLanguageUseCase getSelectedLanguagesUseCase;
    private final Scheduler uiScheduler;
    private final Scheduler computationScheduler;
    private final PublishSubject<String> retrySubject;

    private Disposable searchDisposable;
    private Disposable languageDisposable;
    private Disposable swapDisposable;

    @Inject
    TranslationPresenter(TranslationView view,
                         TranslationUseCase translateUseCase,
                         GetSelectedLanguageUseCase getSelectedLanguagesUseCase,
                         SelectLanguageUseCase selectLanguageUseCase,
                         PublishSubject<String> retrySubject,
                         @Named("ui") Scheduler uiScheduler,
                         @Named("computation") Scheduler computationScheduler) {
        this.view = view;
        this.translateUseCase = translateUseCase;
        this.getSelectedLanguagesUseCase = getSelectedLanguagesUseCase;
        this.selectLanguageUseCase = selectLanguageUseCase;
        this.uiScheduler = uiScheduler;
        this.retrySubject = retrySubject;
        this.computationScheduler = computationScheduler;
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

            if (charSequence.length() == 0) {
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
                    view.setSrcLabel(pair.getSrc());
                    view.setDstLabel(pair.getDst());
                });

        searchDisposable = searchTextObservable
                .map(CharSequence::toString)
                .doOnNext(t -> Timber.d("received new text: %s", t))
                .distinctUntilChanged(String::equals)
                .mergeWith(retrySubject)
                .doOnNext(this::showProgress)
                .doOnNext(s -> Timber.d("before debounce: %s", s))
                .debounce(DELAY, TimeUnit.SECONDS, computationScheduler)
                .doOnNext(s -> Timber.d("after debounce: %s", s))
                .switchMap(translateUseCase::run)
                .doOnNext(r -> Timber.d("after switch map: %s", r))
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

    private void processResult(DisplayTranslateResult result) {
        Timber.d("process result: %s", result.textTranslated);
        if (result.isFound) {
            processFoundResult(result);
        } else {
            processEmptyResult(result);
        }
    }

    private void processFoundResult(DisplayTranslateResult result) {
        view.showTranslation(result);
        view.hideProgress();
        view.showClearBtn();

        if (result.isOffline) {
            view.showOffline();
        } else {
            view.hideOffline();
        }
    }

    private void processEmptyResult(DisplayTranslateResult result) {
        if (result.isError) {
            view.showErrorLayout();
            view.clearTranslation();
        } else {
            view.showNotFound();
        }

        view.hideProgress();
        view.showClearBtn();
        view.hideOffline();
    }

    public void swapDirection() {
        swapDisposable = selectLanguageUseCase
                .swap()
                .observeOn(uiScheduler)
                .subscribe(view::clearResult);
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
