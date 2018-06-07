package com.andyanika.usecases;

import com.andyanika.translator.common.interfaces.LocalRepository;
import com.andyanika.translator.common.interfaces.usecase.RemoveFavoriteUseCase;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class RemoveFavoriteUseCaseImpl implements RemoveFavoriteUseCase {
    private final LocalRepository repository;

    @Inject
    public RemoveFavoriteUseCaseImpl(LocalRepository repository) {
        this.repository = repository;
    }

    @Override
    public Completable run(Integer wordId) {
        return Completable
                .fromRunnable(() -> repository.removeFavorite(wordId))
                .subscribeOn(Schedulers.io());
    }
}
