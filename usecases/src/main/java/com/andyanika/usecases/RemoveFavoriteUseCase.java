package com.andyanika.usecases;

import com.andyanika.translator.common.LocalRepository;

import javax.inject.Inject;

public class RemoveFavoriteUseCase implements Usecase<Integer, Void> {
    private final LocalRepository repository;

    @Inject
    public RemoveFavoriteUseCase(LocalRepository repository) {
        this.repository = repository;
    }

    @Override
    public Void run(Integer wordId) {
        repository.removeFavorite(wordId);
        return null;
    }
}
