package com.andyanika.translator.features.translate;


import android.os.Handler;
import android.support.annotation.NonNull;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRequest;
import com.andyanika.translator.di.FragmentScope;
import com.andyanika.usecases.TranslateUseCase;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);

                    final TranslateResult result = translateUseCase.run(new TranslationRequest(text, "en"));
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.showTranslation(result);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.showErrorLayout();
                        }
                    });
                } finally {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.hideProgress();
                        }
                    });
                }
            }
        }).start();
    }

    public void onTextChanged(@NonNull final String text) {
        translate(text);
    }
}
