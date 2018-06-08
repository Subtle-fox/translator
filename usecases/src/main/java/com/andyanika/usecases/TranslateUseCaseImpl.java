package com.andyanika.usecases;

import com.andyanika.translator.common.interfaces.LocalRepository;
import com.andyanika.translator.common.interfaces.RemoteRepository;
import com.andyanika.translator.common.interfaces.usecase.TranslationUseCase;
import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.TranslateDirection;
import com.andyanika.translator.common.models.TranslateRequest;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.ui.DisplayTranslateResult;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;

class TranslateUseCaseImpl implements TranslationUseCase {
    private final LocalRepository localRepository;
    private final RemoteRepository remoteRepository;
    private final Scheduler ioScheduler;

    @Inject
    TranslateUseCaseImpl(LocalRepository localRepository, RemoteRepository remoteRepository, @Named("io") Scheduler ioScheduler) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
        this.ioScheduler = ioScheduler;
    }

    @Override
    public Observable<DisplayTranslateResult> run(@NonNull String srcText) {
        if (srcText.isEmpty()) {
            return Observable.empty();
        }

        return getTranslationRequest(srcText)
                .flatMap(request ->
                        translateLocally(request)
                                .onErrorResumeNext(translateRemotely(request)))
                .subscribeOn(ioScheduler);
    }

    Observable<TranslateRequest> getTranslationRequest(String srcText) {
        return Observable
                .zip(localRepository.getSrcLanguage(), localRepository.getDstLanguage(), TranslateDirection<LanguageCode>::new)
                .take(1)
                .map(direction -> new TranslateRequest(srcText, direction))
                .doOnNext(translateRequest -> System.out.println("request next"));
    }

    Observable<DisplayTranslateResult> translateLocally(TranslateRequest request) {
        return localRepository.translate(request)
                .map(r -> new DisplayTranslateResult(r, true))
                .toObservable()
                .doOnComplete(() -> System.out.println("local completed"))
                .doOnError(t -> System.out.println("### local error"));
    }

    Observable<DisplayTranslateResult> translateRemotely(TranslateRequest request) {
        return remoteRepository.translate(request)
                .map(result -> {
                    System.out.println("remote next" + result.textDst);
                    return getDisplayTranslateResult(request, result);
                })
                .doOnComplete(() -> System.out.println("remote completed"))
                .doOnError(t -> System.out.println("remote error"))
                .onErrorReturnItem(DisplayTranslateResult.createEmptyResult(request.text, request.direction, true));
    }

    private DisplayTranslateResult getDisplayTranslateResult(TranslateRequest request, TranslateResult result) {
        if (!request.text.equalsIgnoreCase(result.textDst)) {
            try {
                System.out.println("save to local");
                localRepository.addTranslation(result);
                return new DisplayTranslateResult(result, false);
            } catch (Exception e) {
                // somethign goes wrong while saving into db
                e.printStackTrace();
                return DisplayTranslateResult.createEmptyResult(request.text, request.direction, false);
            }
        } else {
            return DisplayTranslateResult.createEmptyResult(request.text, request.direction, false);
        }
    }
}
