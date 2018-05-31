package com.andyanika.usecases;

import com.andyanika.translator.common.LocalRepository;
import com.andyanika.translator.common.RemoteRepository;
import com.andyanika.translator.common.models.TranslateDirection;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRequest;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public class TranslateUseCase {
    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;

    @Inject
    public TranslateUseCase(LocalRepository localRepository, RemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    public Observable<TranslateResult> run(@NonNull String text) {
        // runs in background thread by default

        TranslateDirection direction = localRepository.getLanguageDirection();
        TranslationRequest request = new TranslationRequest(text, direction);
        if (text.isEmpty()) {
            return Observable.empty();
        }

        System.out.println("try translate");


        Observable<TranslateResult> remoteObservable = remoteRepository.translate(request)
                .filter(translateResult -> !text.equalsIgnoreCase(translateResult.textTranslated))
                .doOnNext(translateResult -> {
                    System.out.println("remote next" + translateResult.textTranslated);

                    if (!text.equalsIgnoreCase(translateResult.textTranslated)) {
                        try {
                            System.out.println("save to local");
                            localRepository.addTranslation(translateResult);
                        } catch (Exception e) {
                            // somethign goes wrong while saving into db
                            e.printStackTrace();
                        }
                    }
                })
                .doOnComplete(() -> System.out.println("remote completed"))
                .doOnError(t -> System.out.println("remote error"))
                .onErrorReturnItem(TranslateResult.createErrorResult(text, direction, true))
                .doOnDispose(() -> System.out.println("remote disposed"));

        Observable<TranslateResult> localObservable = localRepository.translate(request)
                .toObservable()
                .doOnComplete(() -> System.out.println("maybe completed"))
                .doOnError(t -> System.out.println("### maybe error"))
                .onErrorResumeNext(remoteObservable)
                .doOnDispose(() -> System.out.println("maybe disposed"));

        return localObservable;
    }
}
