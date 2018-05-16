package com.andyanika.usecases;

import com.andyanika.translator.common.LocalRepository;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;

public class RemoveFavoriteUseCase implements Usecase<Integer, Void> {
    private final LocalRepository repository;

    @Inject
    public RemoveFavoriteUseCase(LocalRepository repository) {
        this.repository = repository;
    }

    @Override
    public Void run(Integer wordId) {
        Schedulers.computation().scheduleDirect(() -> repository.removeFavorite(wordId));
        return null;
    }
}
