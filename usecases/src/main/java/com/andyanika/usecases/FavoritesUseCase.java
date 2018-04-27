package com.andyanika.usecases;

import com.andyanika.translator.common.LocalRepository;
import com.andyanika.translator.common.models.TranslateResult;

import javax.inject.Inject;
import java.util.List;

public class FavoritesUseCase implements Usecase<String, List<TranslateResult>> {
    private LocalRepository repository;

    @Inject
    public FavoritesUseCase(LocalRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TranslateResult> run(String filter) {
        return repository.getFavorites();
    }
}
