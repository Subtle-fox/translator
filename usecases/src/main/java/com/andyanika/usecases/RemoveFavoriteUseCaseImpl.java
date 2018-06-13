package com.andyanika.usecases;

import com.andyanika.translator.common.interfaces.LocalRepository;
import com.andyanika.translator.common.interfaces.usecase.RemoveFavoriteUseCase;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Scheduler;

class RemoveFavoriteUseCaseImpl implements RemoveFavoriteUseCase {
    private final LocalRepository repository;
    private final Scheduler ioScheduler;

    @Inject
    RemoveFavoriteUseCaseImpl(LocalRepository repository, @Named("io") Scheduler ioScheduler) {
        this.repository = repository;
        this.ioScheduler = ioScheduler;
    }

    @Override
    public Completable run(Integer wordId) {
        return Completable
                .fromRunnable(() -> repository.removeFavorite(wordId))
                .subscribeOn(ioScheduler);
    }
}
