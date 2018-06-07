package com.andyanika.usecases;

import com.andyanika.translator.common.interfaces.LocalRepository;
import com.andyanika.translator.common.interfaces.RemoteRepository;
import com.andyanika.translator.common.interfaces.usecase.TranslationUseCase;
import com.andyanika.translator.common.models.TranslateDirection;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRequest;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public class TranslateUseCaseImpl implements TranslationUseCase {
    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;

    @Inject
    public TranslateUseCaseImpl(LocalRepository localRepository, RemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    @Override
    public Observable<TranslateResult> run(@NonNull String srcText) {
        if (srcText.isEmpty()) {
            return Observable.empty();
        }

        // runs in background thread by default
        Observable<TranslationRequest> requestObservable = localRepository.getSrcLanguage()
                .zipWith(localRepository.getDstLanguage(), TranslateDirection::new)
                .take(1)
                .map(direction -> new TranslationRequest(srcText, direction))
                .cache()
                .doOnNext(x -> System.out.println("request next"));

        System.out.println("try translate");
        Observable<TranslateResult> remoteObservable = requestObservable.flatMap(request -> remoteRepository.translate(request)
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
                            return TranslateResult.createEmptyResult(srcText, request.direction, false);
                        }
                    } else {
                        return TranslateResult.createEmptyResult(srcText, request.direction, false);
                    }
                })
                .doOnComplete(() -> System.out.println("remote completed"))
                .doOnError(t -> System.out.println("remote error"))
                .doOnDispose(() -> System.out.println("remote disposed"))
                .onErrorReturnItem(TranslateResult.createEmptyResult(srcText, request.direction, true)));

        Observable<TranslateResult> localObservable = requestObservable.flatMap( request -> localRepository.translate(request)
                .toObservable()
                .doOnComplete(() -> System.out.println("local completed"))
                .doOnError(t -> System.out.println("### local error"))
                .doOnDispose(() -> System.out.println("local disposed"))
                .onErrorResumeNext(remoteObservable));

        return localObservable;
    }
}
