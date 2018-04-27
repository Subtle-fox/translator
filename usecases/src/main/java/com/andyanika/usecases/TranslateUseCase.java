package com.andyanika.usecases;

import com.andyanika.translator.common.LocalRepository;
import com.andyanika.translator.common.RemoteRepository;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRequest;

import javax.inject.Inject;

public class TranslateUseCase implements Usecase<TranslationRequest, TranslateResult> {
    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;

    @Inject
    public TranslateUseCase(LocalRepository localRepository, RemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    @Override
    public TranslateResult run(TranslationRequest request) {
        throw new RuntimeException();
//        TranslateResult result = remoteRepository.translate(request);
//        if (result == null) {
//            result = localRepository.translate(request);
//        }
//        return result;
    }
}
