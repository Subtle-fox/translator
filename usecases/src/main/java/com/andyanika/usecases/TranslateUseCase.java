package com.andyanika.usecases;

import com.andyanika.translator.common.LocalRepository;
import com.andyanika.translator.common.RemoteRepository;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRequest;

import java.io.IOException;

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
        if (request.text.isEmpty()) {
            return new TranslateResult("", "", request.languageSrc, request.languageDst);
        }

        TranslateResult translateResult = null;
        try {
            translateResult = remoteRepository.translate(request);
        } catch (IOException e) {
            // go to offline
            e.printStackTrace();
        }

        if (translateResult != null) {
            try {
                long wordId = localRepository.addTranslation(translateResult);
            } catch (Exception e) {
                // somethign goes wrong while saving into db
                e.printStackTrace();
            }
        } else {
            translateResult = localRepository.translate(request);
        }

        return translateResult;
    }
}
