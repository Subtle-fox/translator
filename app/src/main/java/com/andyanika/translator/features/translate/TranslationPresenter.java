package com.andyanika.translator.features.translate;


import android.os.Handler;
import android.support.annotation.NonNull;

import com.andyanika.translator.common.models.TranslateResult;
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
    private static final long DELAY = TimeUnit.SECONDS.toMillis(3);

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
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    translateInternal(text);
                                }
                            }
                , 1000L);
    }

    private void translateInternal(final String text) {
        if (text.isEmpty()) {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final TranslateResult result = translateUseCase.translate(text);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (result != null) {
                                view.showTranslation(result);
                            } else {
                                view.showErrorLayout();
                            }
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
