package com.andyanika.usecases;

import com.andyanika.translator.common.LocalRepository;

import javax.inject.Inject;

public class AddFavoriteUseCase implements Usecase<Integer, Void> {
    private final LocalRepository repository;

    @Inject
    public AddFavoriteUseCase(LocalRepository repository) {
        this.repository = repository;
    }

    @Override
    public Void run(Integer wordId) {
        repository.addFavorites(wordId);
        return null;
    }
}
