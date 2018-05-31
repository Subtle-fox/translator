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

    public Observable<TranslateResult> run(@NonNull String srcText) {
        // runs in background thread by default

        TranslateDirection direction = localRepository.getLanguageDirection();
        TranslationRequest request = new TranslationRequest(srcText, direction);
        if (srcText.isEmpty()) {
            return Observable.empty();
        }

        System.out.println("try translate");


        Observable<TranslateResult> remoteObservable = remoteRepository.translate(request)
                .map(result -> {
                    System.out.println("remote next" + result.textTranslated);
                    if (!srcText.equalsIgnoreCase(result.textTranslated)) {
                        try {
                            System.out.println("save to local");
                            localRepository.addTranslation(result);
                            return result;
                        } catch (Exception e) {
                            // somethign goes wrong while saving into db
                            e.printStackTrace();
                            return TranslateResult.createEmptyResult(srcText, direction, false);
                        }
                    } else {
                        return TranslateResult.createEmptyResult(srcText, direction, false);
                    }
                })
                .doOnComplete(() -> System.out.println("remote completed"))
                .doOnError(t -> System.out.println("remote error"))
                .doOnDispose(() -> System.out.println("remote disposed"))
                .onErrorReturnItem(TranslateResult.createEmptyResult(srcText, direction, true));

        Observable<TranslateResult> localObservable = localRepository.translate(request)
                .toObservable()
                .doOnComplete(() -> System.out.println("local completed"))
                .doOnError(t -> System.out.println("### local error"))
                .doOnDispose(() -> System.out.println("local disposed"))
                .onErrorResumeNext(remoteObservable);

        return localObservable;
    }
}
