package com.andyanika.usecases;

import com.andyanika.translator.common.LocalRepository;
import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.TranslateDirection;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Scheduler;

public class SelectLanguageUseCase {
    private final LocalRepository repository;
    private Scheduler ioScheduler;

    @Inject
    public SelectLanguageUseCase(LocalRepository repository, @Named("io") Scheduler ioScheduler) {
        this.repository = repository;
        this.ioScheduler = ioScheduler;
    }

    public Completable setSrc(LanguageCode code) {
        return Completable
                .fromAction(() -> {
                    TranslateDirection oldDirection = repository.getLanguageDirection();
                    TranslateDirection newDirection = normalize(new TranslateDirection(code, oldDirection.dst), oldDirection);
                    repository.setLanguageDirection(newDirection);
                })
                .onErrorComplete()
                .subscribeOn(ioScheduler);
    }

    public Completable setDst(LanguageCode code) {
        return Completable
                .fromAction(() -> {
                    TranslateDirection oldDirection = repository.getLanguageDirection();
                    TranslateDirection newDirection = normalize(new TranslateDirection(oldDirection.src, code), oldDirection);
                    repository.setLanguageDirection(newDirection);
                })
                .onErrorComplete()
                .subscribeOn(ioScheduler);
    }

    public Completable swap() {
        return Completable
                .fromRunnable(() -> {
                    TranslateDirection oldDirection = repository.getLanguageDirection();
                    TranslateDirection newDirection = new TranslateDirection(oldDirection.dst, oldDirection.src);
                    repository.setLanguageDirection(newDirection);
                })
                .onErrorComplete()
                .subscribeOn(ioScheduler);
    }

    TranslateDirection normalize(TranslateDirection newDirection, TranslateDirection oldDirection) {
        if (newDirection.src == oldDirection.dst) {
            // swap
            return new TranslateDirection(newDirection.src, oldDirection.src);
        } else if (newDirection.dst == oldDirection.src) {
            // swap
            return new TranslateDirection(oldDirection.dst, newDirection.src);
        }
        return new TranslateDirection(newDirection.src, newDirection.dst);
    }
}
