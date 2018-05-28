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

        System.out.println("try translate");

        Observable<TranslateResult> localObservable = localRepository.translate(request)
                .doOnComplete(() -> System.out.println("maybe completed"))
                .toObservable();

        Observable<TranslateResult> remoteObservable = remoteRepository.translate(request)
                .doOnNext(x -> System.out.println("remote next"))
                .doOnComplete(() -> System.out.println("remote completed"))
                .doOnError(t -> System.out.println("remote error"))
                .onErrorResumeNext(localObservable);

        return localObservable.concatWith(remoteObservable);
    }

//    public TranslateResult run(TranslationRequest request) {
//        if (request.text.isEmpty()) {
//            return new TranslateResult("", "", request.direction);
//        }
//
//        TranslateResult translateResult = null;
//        try {
////            translateResult = remoteRepository.translate(request);
//        } catch (IOException e) {
//            // go to offline
//            e.printStackTrace();
//        }
//
//        if (translateResult != null) {
//            try {
//                long wordId = localRepository.addTranslation(translateResult);
//            } catch (Exception e) {
//                // somethign goes wrong while saving into db
//                e.printStackTrace();
//            }
//        } else {
////            translateResult = localRepository.translate(request);
//        }
//
//        return translateResult;
//    }
//
//    public TranslateResult translate(@NonNull String text) {
//        TranslateDirection direction = localRepository.getLanguageDirection();
//        TranslationRequest request = new TranslationRequest(text, direction);
//
//        TranslateResult translateResult = null;
//        try {
////            translateResult = remoteRepository.translate(request);
//        } catch (IOException e) {
//            // go to offline
//            e.printStackTrace();
//        }
//
//        if (translateResult != null) {
//            try {
//                long wordId = localRepository.addTranslation(translateResult);
//            } catch (Exception e) {
//                // somethign goes wrong while saving into db
//                e.printStackTrace();
//            }
//        } else {
////            translateResult = localRepository.translate(request);
//        }
//
//        return translateResult;
//    }
}
