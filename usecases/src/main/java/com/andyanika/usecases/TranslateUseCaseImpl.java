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
                                .onErrorResumeNext(translateRemotely(request)))
                .subscribeOn(ioScheduler);
    }

    Observable<TranslateRequest> getTranslationRequest(String srcText) {
        return Observable
                .zip(localRepository.getSrcLanguage(), localRepository.getDstLanguage(), TranslateDirection<LanguageCode>::new)
                .take(1)
                .map(direction -> new TranslateRequest(srcText, direction))
                .doOnNext(translateRequest -> Timber.d("translation request: %s", translateRequest.text));
    }

    Observable<DisplayTranslateResult> translateLocally(TranslateRequest request) {
        return localRepository.translate(request)
                .doOnSuccess(r -> Timber.d("local next: %s", r.textDst))
                .map(r -> new DisplayTranslateResult(r, true))
                .toObservable()
                .doOnComplete(() -> Timber.d("local completed"))
                .doOnError(t -> Timber.e("local error", t));
    }

    Observable<DisplayTranslateResult> translateRemotely(TranslateRequest request) {
        return remoteRepository.translate(request)
                .doOnNext(r -> Timber.d("remote next: %s", r.textDst))
                .map(result -> getDisplayTranslateResult(request, result))
                .doOnComplete(() -> Timber.d("remote completed"))
                .doOnError(t -> Timber.e("remote error", t))
                .onErrorReturnItem(DisplayTranslateResult.createEmptyResult(request.text, request.direction, true));
    }

    private DisplayTranslateResult getDisplayTranslateResult(TranslateRequest request, TranslateResult result) {
        if (!request.text.equalsIgnoreCase(result.textDst)) {
            try {
                Timber.d("save to local repository");
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
