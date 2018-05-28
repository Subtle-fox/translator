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

    public Observable<TranslateResult> translate(@NonNull String text) {
        TranslateDirection direction = localRepository.getLanguageDirection();
        TranslationRequest request = new TranslationRequest(text, direction);
        if (text.isEmpty()) {
            return Observable.empty();
        }

        System.out.println("try translate");
        Observable<TranslateResult> localObservable = localRepository.translate(request)
                .doOnComplete(() -> System.out.println("maybe completed"))
                .toObservable();

        Observable<TranslateResult> remoteObservable = remoteRepository.translate(request)
                .doOnNext(translateResult -> {
                    System.out.println("remote next");
                    try {
                        System.out.println("save to local");
                        localRepository.addTranslation(translateResult);
                    } catch (Exception e) {
                        // somethign goes wrong while saving into db
                        e.printStackTrace();
                    }
                })
                .doOnComplete(() -> System.out.println("remote completed"))
                .doOnError(t -> System.out.println("remote error"))
                .onErrorResumeNext(localObservable);

        return localObservable.concatWith(remoteObservable);
    }
}
