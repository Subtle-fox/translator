package com.andyanika.usecases;

import com.andyanika.translator.common.interfaces.LocalRepository;
import com.andyanika.translator.common.interfaces.usecase.AddFavoriteUseCase;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Scheduler;

class AddFavoriteUseCaseImpl implements AddFavoriteUseCase {
    private final LocalRepository repository;
    private final Scheduler ioScheduler;

    @Inject
    AddFavoriteUseCaseImpl(LocalRepository repository, @Named("io") Scheduler ioScheduler) {
        this.repository = repository;
        this.ioScheduler = ioScheduler;
    }

    @Override
    public Completable run(Integer wordId) {
        return Completable
                .fromRunnable(() -> repository.addFavorites(wordId))
                .subscribeOn(ioScheduler);
    }
}
