package com.andyanika.usecases;

import com.andyanika.translator.common.interfaces.LocalRepository;
import com.andyanika.translator.common.interfaces.usecase.AddFavoriteUseCase;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class AddFavoriteUseCaseImpl implements AddFavoriteUseCase {
    private final LocalRepository repository;

    @Inject
    public AddFavoriteUseCaseImpl(LocalRepository repository) {
        this.repository = repository;
    }

    @Override
    public Completable run(Integer wordId) {
        return Completable
                .fromRunnable(() -> repository.addFavorites(wordId))
                .subscribeOn(Schedulers.io());
    }
}
