package com.andyanika.usecases;

import com.andyanika.translator.common.LocalRepository;
import com.andyanika.translator.common.RemoteRepository;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRequest;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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

        return localRepository.translate(request).toObservable();
//        return remoteRepository.translate(request).doOnNext(new Consumer<TranslateResult>() {
//                    @Override
//                    public void accept(TranslateResult translateResult) throws Exception {
//                        localRepository.addTranslation(translateResult)  ;
//                    }
//                });

//        Observable<TranslateResult> translateResultObserv = Observable.concatArray(
//                localRepository.translate(request).toObservable(),
//                remoteRepository.translate(request).doOnNext(new Consumer<TranslateResult>() {
//                    @Override
//                    public void accept(TranslateResult translateResult) throws Exception {
//                        localRepository.addTranslation(translateResult)  ;
//                    }
//                })
//        );
//        return translateResultObserv;


//        Observable<TranslateResult> translateResultObservable = remoteRepository.translate(request)
//                .subscribeOn(Schedulers.io())
//                .doAfterNext(new Consumer<TranslateResult>() {
//                    @Override
//                    public void accept(TranslateResult translateResult) throws Exception {
//                        localRepository.addTranslation(translateResult);
//                    }
//                });

//        Observable.fromCallable(new Callable<TranslateResult>() {
//            @Override
//            public TranslateResult call() throws Exception {
//                try {
//                    TranslateResult translateResult = remoteRepository.translate(request);
//                    long wordId = localRepository.addTranslation(translateResult);

//                } catch (IOException e) {
//                     go to offline
//                    e.printStackTrace();
//                }

//                if (translateResult != null) {
//                    try {
//                        long wordId = localRepository.addTranslation(translateResult);
//                    } catch (Exception e) {
//                         somethign goes wrong while saving into db
//                        e.printStackTrace();
//                    }
//                } else {
//                    translateResult = localRepository.translate(request);
//                }
//
//                return translateResult;
//            }
//        });

//        return localRepository.translate(request).toObservable();
    }
}
