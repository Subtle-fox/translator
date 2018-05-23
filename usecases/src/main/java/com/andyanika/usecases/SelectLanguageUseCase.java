package com.andyanika.usecases;

import com.andyanika.translator.common.LocalRepository;
import com.andyanika.translator.common.models.LanguageDirection;

import javax.inject.Inject;

public class SelectLanguageUseCase {
    private final LocalRepository repository;

    @Inject
    public SelectLanguageUseCase(LocalRepository repository) {
        this.repository = repository;
    }

    public void run(LanguageDirection newDirection) {
        LanguageDirection oldDirection = repository.getLanguageDirection();
        if (newDirection.src == oldDirection.src && newDirection.dst == oldDirection.dst) {
            return;
        }
        LanguageDirection direction = normalize(newDirection, oldDirection);
        repository.setLanguageDirection(direction);
    }

    LanguageDirection normalize(LanguageDirection newDirection, LanguageDirection oldDirection) {
        if (newDirection.src == oldDirection.dst) {
            // swap
            return new LanguageDirection(newDirection.src, oldDirection.src);
        } else if (newDirection.dst == oldDirection.src) {
            // swap
            return new LanguageDirection(oldDirection.dst, newDirection.src);
        }
        return new LanguageDirection(newDirection.src, newDirection.dst);
    }
}
