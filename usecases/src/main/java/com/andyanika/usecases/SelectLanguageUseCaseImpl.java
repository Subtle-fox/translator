package com.andyanika.usecases;

import com.andyanika.translator.common.interfaces.LocalRepository;
import com.andyanika.translator.common.interfaces.usecase.SelectLanguageUseCase;
import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.TranslateDirection;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Scheduler;

public class SelectLanguageUseCaseImpl implements SelectLanguageUseCase {
    private final LocalRepository repository;
    private Scheduler ioScheduler;

    @Inject
    public SelectLanguageUseCaseImpl(LocalRepository repository, @Named("io") Scheduler ioScheduler) {
        this.repository = repository;
        this.ioScheduler = ioScheduler;
    }

    @Override
    public Completable setSrc(LanguageCode code) {
        return Completable.fromObservable(
                repository.getSrcLanguage()
                        .zipWith(repository.getDstLanguage(), TranslateDirection::new)
                        .take(1)
                        .map(oldDirection -> normalize(new TranslateDirection(code, oldDirection.dst), oldDirection))
                        .doOnNext(repository::setLanguageDirection));
    }

    @Override
    public Completable setDst(LanguageCode code) {
        return Completable.fromObservable(
                repository.getSrcLanguage()
                        .zipWith(repository.getDstLanguage(), TranslateDirection::new)
                        .take(1)
                        .map(oldDirection -> normalize(new TranslateDirection(oldDirection.src, code), oldDirection))
                        .doOnNext(repository::setLanguageDirection));
    }

    @Override
    public Completable swap() {
        return Completable.fromObservable(
                repository.getSrcLanguage()
                        .zipWith(repository.getDstLanguage(), (src, dst) -> new TranslateDirection(dst, src))
                        .take(1)
                        .doOnNext(repository::setLanguageDirection));
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
