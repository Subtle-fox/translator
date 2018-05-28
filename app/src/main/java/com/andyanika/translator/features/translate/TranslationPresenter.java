package com.andyanika.translator.features.translate;


import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.TranslationRequest;
import com.andyanika.translator.di.FragmentScope;
import com.andyanika.usecases.GetSelectedLanguagesUseCase;
import com.andyanika.usecases.SelectLanguageUseCase;
import com.andyanika.usecases.TranslateUseCase;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@FragmentScope
public class TranslationPresenter {
    private static final long DELAY = TimeUnit.SECONDS.toMillis(3);

    private final TranslationView view;
    private TranslateUseCase translateUseCase;
    private SelectLanguageUseCase selectLanguageUseCase;
    private final Handler handler;
    private Disposable disposable;

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
        disposable = view.getSearchTextObservable()
                .debounce(2, TimeUnit.SECONDS)
                .map(CharSequence::toString)
                .subscribeOn(Schedulers.io())
//                .doAfterNext(s -> {
//                    Log.d("#", s);
//                })
                .flatMap(str -> translateUseCase.run(new TranslationRequest(str, LanguageCode.RU, LanguageCode.EN)))
//                .doAfterNext(s -> {
//                    Log.d("#", s.toString());
//                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        translateResult -> {
                            Log.d("###", "next");
                            view.showTranslation(translateResult);
                        },
                        throwable -> {
                            Log.d("###", "error");
                            view.showErrorLayout();
                        },
                        () -> {
                            Log.d("###", "completed");
                            view.hideProgress();
                            view.showClearBtn();
                        });

//                .distinctUntilChanged()
    }

    public void unsubscribe() {
        if (disposable != null) {
            disposable.dispose();
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
