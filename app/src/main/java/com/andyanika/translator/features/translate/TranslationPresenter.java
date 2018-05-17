package com.andyanika.translator.features.translate;


import android.os.Handler;
import android.support.annotation.NonNull;

import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRequest;
import com.andyanika.translator.di.FragmentScope;
import com.andyanika.usecases.TranslateUseCase;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@FragmentScope
public class TranslationPresenter {
    private static final long DELAY = TimeUnit.SECONDS.toMillis(3);

    private final TranslationView view;
    private TranslateUseCase translateUseCase;
    private final Handler handler;

    @Inject
    TranslationPresenter(TranslationView view, TranslateUseCase translateUseCase) {
        this.view = view;
        this.translateUseCase = translateUseCase;
        this.handler = new Handler();
    }

    public void translate(@NonNull final String text) {
        view.showProgress();
        view.hideErrorLayout();
        handler.removeCallbacksAndMessages(null);

        handler.postDelayed(() -> {
            translateUseCase.run(new TranslationRequest(text, LanguageCode.RU, LanguageCode.EN))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<TranslateResult>() {
                        @Override
                        public void onNext(TranslateResult result) {
                            view.showTranslation(result);
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.showErrorLayout();
                            onComplete();
                        }

                        @Override
                        public void onComplete() {
                            view.hideProgress();
                            view.showClearBtn();
                        }
                    });
        }, 1000);
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
        view.clearTranslation();
        handler.removeCallbacksAndMessages(null);
    }
}
