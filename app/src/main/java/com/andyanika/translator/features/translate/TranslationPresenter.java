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

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@FragmentScope
public class TranslationPresenter {
    private static final long DELAY = 2;

    private final TranslationView view;
    private TranslateUseCase translateUseCase;
    private SelectLanguageUseCase selectLanguageUseCase;

    private GetSelectedLanguagesUseCase getSelectedLanguagesUseCase;

    private CompositeDisposable compositeDisposable;

    @Inject
    TranslationPresenter(TranslationView view,
                         TranslateUseCase translateUseCase,
                         GetSelectedLanguagesUseCase getSelectedLanguagesUseCase,
                         SelectLanguageUseCase selectLanguageUseCase) {
        this.view = view;
        this.translateUseCase = translateUseCase;
        this.getSelectedLanguagesUseCase = getSelectedLanguagesUseCase;
        this.selectLanguageUseCase = selectLanguageUseCase;
        this.compositeDisposable = new CompositeDisposable();
    }

    public void translate(@NonNull final String text) {
        // TODO: 29.05.2018
    }

    public void clear() {
        view.hideClearBtn();
        view.hideErrorLayout();
        view.clearResult();
    }

    private void showProgress() {
        AndroidSchedulers.mainThread().scheduleDirect(() -> {
            System.out.println("received on each");
            view.hideClearBtn();
            view.hideErrorLayout();
            view.showProgress();
            view.hideOffline();
        });
    }

    public void subscribe() {
        compositeDisposable.add(
                view.getSearchTextObservable()
                        .doOnNext(x -> System.out.println("begining " + x))
                        .filter(charSequence -> !TextUtils.isEmpty(charSequence))
                        .doOnNext(x -> System.out.println("received before debounce " + x))
                        .doOnNext(charSequence -> showProgress())
                        .debounce(DELAY, TimeUnit.SECONDS)
                        .doOnNext(x -> System.out.println("received after debounce " + x))
                        .map(CharSequence::toString)
                        .flatMap(str -> translateUseCase.translate(str))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                translateResult -> {
                                    System.out.println("--- >>> next: " + translateResult.textTranslated);
                                    processResult(translateResult);
                                },
                                throwable -> {
                                    System.out.println("--- ### error");
                                    throwable.printStackTrace();

                                    view.showErrorLayout();
                                    view.clearTranslation();
                                    view.hideProgress();
                                    view.showClearBtn();
                                    view.hideOffline();
                                },
                                () -> System.out.println("--- $$$ completed")));
    }

    private void processResult(TranslateResult result) {
        if (!result.isSuccess) {
            view.showErrorLayout();
            view.clearTranslation();
            view.hideProgress();
            view.showClearBtn();
            if (!result.isOffline) {
                view.showOffline();
            }
        } else {
            view.showTranslation(result);
            view.hideProgress();
            view.showClearBtn();

            if (!result.isOffline) {
                view.hideOffline();
            }
        }
    }

    public void swapDirection() {
        compositeDisposable.add(
                selectLanguageUseCase.swap()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::load));
    }

    public void load() {
        System.out.println("subscribe");

        Observable<LanguageDescription> cache = getSelectedLanguagesUseCase.run().cache();
        compositeDisposable.add(cache
                .doOnNext(s -> System.out.println("on next 1 : " + s.description))
                .filter(ls -> ls.isSrc)
                .doOnNext(s -> System.out.println("on map 1 : " + s.description))
                .map(ls -> ls.description)
                .subscribe(view::setSrcLabel));

        compositeDisposable.add(cache
                .doOnNext(s -> System.out.println("on next 2 : " + s.description))
                .filter(ls -> !ls.isSrc)
                .doOnNext(s -> System.out.println("on map 2 : " + s.description))
                .map(ls -> ls.description)
                .subscribe(view::setDstLabel));
    }

    void dispose() {
        compositeDisposable.dispose();
    }
}
