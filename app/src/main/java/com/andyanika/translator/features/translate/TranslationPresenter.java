package com.andyanika.translator.features.translate;


import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.TranslationRequest;
import com.andyanika.translator.di.FragmentScope;
import com.andyanika.usecases.TranslateUseCase;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@FragmentScope
public class TranslationPresenter {
    private static final long DELAY = TimeUnit.SECONDS.toMillis(3);

    private final TranslationView view;
    private final Handler handler;
    private TranslateUseCase translateUseCase;
    private Disposable disposable;

    @Inject
    TranslationPresenter(TranslationView view, TranslateUseCase translateUseCase) {
        this.view = view;
        this.translateUseCase = translateUseCase;
        this.handler = new Handler();
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
}
