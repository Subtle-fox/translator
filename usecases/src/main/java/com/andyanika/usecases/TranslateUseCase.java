package com.andyanika.usecases;

import com.andyanika.translator.common.LocalRepository;
import com.andyanika.translator.common.RemoteRepository;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRequest;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URLEncoder;

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

        try {
            return remoteRepository.translate(request);
        } catch (IOException e) {
            return localRepository.translate(request);
        }
    }
}
