package com.andyanika.translator.features.translate;


import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.andyanika.translator.common.models.LanguageDescription;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.di.FragmentScope;
import com.andyanika.usecases.GetSelectedLanguagesUseCase;
import com.andyanika.usecases.SelectLanguageUseCase;
import com.andyanika.usecases.TranslateUseCase;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

@FragmentScope
public class TranslationPresenter {
    private static final long DELAY = 2;

    private final TranslationView view;
    private TranslateUseCase translateUseCase;
    private SelectLanguageUseCase selectLanguageUseCase;
    private Scheduler uiScheduler;

    private GetSelectedLanguagesUseCase getSelectedLanguagesUseCase;

    private CompositeDisposable compositeDisposable;

    private PublishSubject<CharSequence> textSearchSubject = PublishSubject.create();

    @Inject
    TranslationPresenter(TranslationView view,
                         TranslateUseCase translateUseCase,
                         GetSelectedLanguagesUseCase getSelectedLanguagesUseCase,
                         SelectLanguageUseCase selectLanguageUseCase,
                         @Named("ui") Scheduler uiScheduler) {
        this.view = view;
        this.translateUseCase = translateUseCase;
        this.getSelectedLanguagesUseCase = getSelectedLanguagesUseCase;
        this.selectLanguageUseCase = selectLanguageUseCase;
        this.uiScheduler = uiScheduler;
        this.compositeDisposable = new CompositeDisposable();
    }

    public void translate(@NonNull final String text) {
        textSearchSubject
                .repeat(1)
                .doOnNext(charSequence -> {
                    textSearchSubject.onNext(charSequence);
                }).subscribe();

        // TODO: 29.05.2018
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
        searchTextObservable.subscribe(textSearchSubject);

        compositeDisposable.add(
                textSearchSubject
                        .map(CharSequence::toString)
                        .distinctUntilChanged(String::equals)
                        .doOnNext(this::showProgress)
                        .debounce(DELAY, TimeUnit.SECONDS)
                        .flatMap(str -> translateUseCase.run(str))
                        .observeOn(uiScheduler)
                        .subscribe(
                                translateResult -> {
                                    System.out.println("--- >>> next: " + translateResult.textTranslated);
                                    processResult(translateResult);
                                },
                                throwable -> {
                                    System.out.println("--- ### error");
                                    processError(throwable);
                                },
                                () -> System.out.println("--- $$$ completed")));
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
        if (result.isFound) {
            view.showTranslation(result);
            view.hideProgress();
            view.showClearBtn();

            if (result.isOffline) {
                view.showOffline();
            } else {
                view.hideOffline();
            }
        } else {
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
    }

    public void swapDirection() {
        compositeDisposable.add(
                selectLanguageUseCase.swap()
                        .subscribeOn(Schedulers.io())
                        .observeOn(uiScheduler)
                        .subscribe(this::load));
    }

    public void load() {
        Observable<LanguageDescription> cache = getSelectedLanguagesUseCase.run().cache();
        compositeDisposable.add(cache
                .filter(ls -> ls.isSrc)
                .map(ls -> ls.description)
                .observeOn(uiScheduler)
                .subscribe(view::setSrcLabel));

        compositeDisposable.add(cache
                .filter(ls -> !ls.isSrc)
                .map(ls -> ls.description)
                .observeOn(uiScheduler)
                .subscribe(view::setDstLabel));
    }

    void dispose() {
        compositeDisposable.dispose();
    }
}
