package com.andyanika.usecases;

import com.andyanika.translator.common.LocalRepository;
import com.andyanika.translator.common.models.TranslationRowModel;

import java.util.List;

import javax.inject.Inject;

public class GetFavoritesUseCase implements Usecase<String, List<TranslationRowModel>> {
    private LocalRepository repository;

    @Inject
    public GetFavoritesUseCase(LocalRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TranslationRowModel> run(String filter) {
        return repository.getFavorites();
    }
}
