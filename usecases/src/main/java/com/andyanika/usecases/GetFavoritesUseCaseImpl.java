package com.andyanika.usecases;

import com.andyanika.translator.common.interfaces.LocalRepository;
import com.andyanika.translator.common.interfaces.usecase.GetFavoritesUseCase;
import com.andyanika.translator.common.models.UiTranslationModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class GetFavoritesUseCaseImpl implements GetFavoritesUseCase {
    private LocalRepository repository;

    @Inject
    public GetFavoritesUseCaseImpl(LocalRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flowable<List<UiTranslationModel>> run(int limit) {
        // runs in background thread by default
        return repository.getFavorites().take(limit);
    }
}
