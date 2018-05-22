package com.andyanika.usecases;

import com.andyanika.translator.common.LocalRepository;
import com.andyanika.translator.common.RemoteRepository;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRequest;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.functions.Consumer;

public class TranslateUseCase implements Usecase<TranslationRequest, Observable<TranslateResult>> {
    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;

    @Inject
    public TranslateUseCase(LocalRepository localRepository, RemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    @Override
    public Observable<TranslateResult> run(TranslationRequest request) {
//        Observable<Integer> observable = Observable.create((ObservableEmitter<TranslateResult> e) -> {
//
//
//            e.onComplete();
//        });

        return  localRepository.translate(request).toObservable();
//
//        return Observable.concat(
//                localRepository.translate(request).toObservable(),
//                remoteRepository.translate(request)
//                        .onExceptionResumeNext(localRepository.translate(request).toObservable())
//                        .flatMap((TranslateResult translateResult) -> {
//                            localRepository.addTranslation(translateResult);
//                            return localRepository.translate(request).toObservable();
//                        }
//                )
//        );
    }
}
