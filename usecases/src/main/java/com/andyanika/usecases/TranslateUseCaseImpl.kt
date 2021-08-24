package com.andyanika.usecases;

import com.andyanika.translator.common.interfaces.LocalRepository;
import com.andyanika.translator.common.interfaces.RemoteRepository;
import com.andyanika.translator.common.interfaces.usecase.TranslationUseCase;
import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.TranslateDirection;
import com.andyanika.translator.common.models.TranslateRequest;
import com.andyanika.translator.common.models.ui.DisplayTranslateResult;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import timber.log.Timber;

class TranslateUseCaseImpl implements TranslationUseCase {
    private final LocalRepository localRepository;
    private final RemoteRepository remoteRepository;
    private final Scheduler ioScheduler;

    @Inject
    TranslateUseCaseImpl(LocalRepository localRepository, @Named("yandex") RemoteRepository remoteRepository, @Named("io") Scheduler ioScheduler) {
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
                                .onErrorResumeWith(translateRemotely(request)))
                .subscribeOn(ioScheduler);
    }

    Observable<TranslateRequest> getTranslationRequest(String srcText) {
        return Observable
                .zip(localRepository.getSrcLanguage(), localRepository.getDstLanguage(), TranslateDirection<LanguageCode>::new)
                .timeout(500, TimeUnit.MILLISECONDS)
                .take(1)
                .map(direction -> new TranslateRequest(srcText, direction))
                .doOnNext(translateRequest -> Timber.d("translation request: %s", translateRequest.getText()));
    }

    Observable<DisplayTranslateResult> translateLocally(TranslateRequest request) {
        return localRepository.translate(request)
                .doOnSuccess(result -> Timber.d("local next: %s", result))
                .map(result -> new DisplayTranslateResult(result, true))
                .toObservable()
                .doOnComplete(() -> Timber.d("local completed"))
                .doOnError(t -> Timber.e(t, "local error"));
    }

    Observable<DisplayTranslateResult> translateRemotely(TranslateRequest request) {
        return remoteRepository.translate(request)
                .doOnNext(result -> Timber.d("remote next: %s", result))
                .flatMap(result -> localRepository.addTranslation(result)
                        .onErrorReturnItem(result)
                        .toObservable())
                .doOnNext(result -> Timber.d("after flat map: %s", result))
                .map(result -> new DisplayTranslateResult(result, false))
                .doOnComplete(() -> Timber.d("remote completed"))
                .defaultIfEmpty(DisplayTranslateResult.createEmptyResult(request, false))
                .doOnError(t -> Timber.e(t, "remote error"))
                .onErrorReturnItem(DisplayTranslateResult.createEmptyResult(request, true));
    }
}
