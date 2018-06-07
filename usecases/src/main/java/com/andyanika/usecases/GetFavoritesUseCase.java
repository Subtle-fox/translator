package com.andyanika.usecases;

import com.andyanika.translator.common.LocalRepository;
import com.andyanika.translator.common.models.TranslationRowModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class GetFavoritesUseCase {
    private LocalRepository repository;

    @Inject
    public GetFavoritesUseCase(LocalRepository repository) {
        this.repository = repository;
    }

    public Flowable<List<TranslationRowModel>> run(int limit) {
        // runs in background thread by default
        return repository.getFavorites().take(limit);
    }
}
