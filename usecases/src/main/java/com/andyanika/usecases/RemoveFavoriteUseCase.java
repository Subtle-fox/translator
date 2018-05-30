package com.andyanika.usecases;

import com.andyanika.translator.common.LocalRepository;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class RemoveFavoriteUseCase {
    private final LocalRepository repository;

    @Inject
    public RemoveFavoriteUseCase(LocalRepository repository) {
        this.repository = repository;
    }

    public Completable run(Integer wordId) {
        return Completable
                .fromRunnable(() -> repository.removeFavorite(wordId))
                .subscribeOn(Schedulers.io());
    }
}
