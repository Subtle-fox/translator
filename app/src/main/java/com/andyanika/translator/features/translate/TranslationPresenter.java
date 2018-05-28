package com.andyanika.translator.features.translate;


import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.andyanika.translator.di.FragmentScope;
import com.andyanika.usecases.GetSelectedLanguagesUseCase;
import com.andyanika.usecases.SelectLanguageUseCase;
import com.andyanika.usecases.TranslateUseCase;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@FragmentScope
public class TranslationPresenter {
    private static final long DELAY = 2;

    private final TranslationView view;
    private TranslateUseCase translateUseCase;
    private SelectLanguageUseCase selectLanguageUseCase;
    private final Handler handler;

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
        this.handler = new Handler();
        this.compositeDisposable = new CompositeDisposable();
    }

    public void translate(@NonNull final String text) {
        view.showProgress();
        view.hideErrorLayout();

//        view.getSearchTextObservable();

    }


    public void onTextChanged(@NonNull final String text) {
        if (text.isEmpty()) {
            clear();
        } else {
            translate(text);
        }
    }

    public void clear() {
        view.hideClearBtn();
        view.hideErrorLayout();
        view.clearResult();
        handler.removeCallbacksAndMessages(null);
    }

    public void subscribe() {
        compositeDisposable.add(
                view.getSearchTextObservable()
                        .doOnNext(x -> System.out.println("received before debounce " + x))
                        .doOnNext(charSequence -> AndroidSchedulers.mainThread().scheduleDirect(() -> {
                            System.out.println("received on each");
                            view.hideClearBtn();
                            view.hideErrorLayout();
                            view.showProgress();
                        }))
                        .subscribeOn(Schedulers.io())
                        .debounce(DELAY, TimeUnit.SECONDS)
//                        .distinctUntilChanged()
                        .doOnNext(x -> System.out.println("received after debounce " + x))
                        .map(CharSequence::toString)
                        .flatMap(str -> translateUseCase.translate(str))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                translateResult -> {
                                    Log.d("###", "next");
                                    view.showTranslation(translateResult);
                                },
                                throwable -> {
                                    Log.d("###", "error");
                                    throwable.printStackTrace();
                                    view.showErrorLayout();
                                },
                                () -> {
                                    Log.d("###", "completed");
                                    view.hideProgress();
                                    view.showClearBtn();
                                }));

//                .distinctUntilChanged()
    }

    public void unsubscribe() {
        dispose();
    }

    public void swapDirection() {
        compositeDisposable.add(
                selectLanguageUseCase.swap()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::load));
    }

    public void load() {
        compositeDisposable.add(
                getSelectedLanguagesUseCase.getSrc()
                        .map(ls -> ls.description)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(view::setSrcLabel));

        compositeDisposable.add(
                getSelectedLanguagesUseCase.getDst()
                        .map(ls -> ls.description)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(view::setDstLabel));
    }

    void dispose() {
        compositeDisposable.dispose();
    }
}
