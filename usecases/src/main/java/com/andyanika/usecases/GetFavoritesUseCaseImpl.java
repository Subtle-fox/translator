package com.andyanika.usecases;

import com.andyanika.translator.common.interfaces.LocalRepository;
import com.andyanika.translator.common.interfaces.usecase.GetFavoritesUseCase;
import com.andyanika.translator.common.models.FavoriteModel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

class GetFavoritesUseCaseImpl implements GetFavoritesUseCase {
    private final LocalRepository repository;
    private final Scheduler ioScheduler;

    @Inject
    GetFavoritesUseCaseImpl(LocalRepository repository, @Named("io") Scheduler ioScheduler) {
        this.repository = repository;
        this.ioScheduler = ioScheduler;
    }

    @Override
    public Flowable<List<FavoriteModel>> run(int limit) {
        return repository
                .getFavorites()
                .subscribeOn(ioScheduler)
                .take(limit);
    }
}
