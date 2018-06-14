package com.andyanika.usecases;

import com.andyanika.translator.common.interfaces.LocalRepository;
import com.andyanika.translator.common.interfaces.usecase.SelectLanguageUseCase;
import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.TranslateDirection;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Scheduler;

class SelectLanguageUseCaseImpl implements SelectLanguageUseCase {
    private final LocalRepository repository;
    private final Scheduler ioScheduler;

    @Inject
    SelectLanguageUseCaseImpl(LocalRepository repository, @Named("io") Scheduler ioScheduler) {
        this.repository = repository;
        this.ioScheduler = ioScheduler;
    }

    @Override
    public Completable setSrc(LanguageCode code) {
        return Completable.fromObservable(
                repository.getSrcLanguage()
                        .subscribeOn(ioScheduler)
                        .zipWith(repository.getDstLanguage(), TranslateDirection<LanguageCode>::new)
                        .take(1)
                        .map(oldDirection -> normalize(new TranslateDirection<>(code, oldDirection.getDst()), oldDirection))
                        .doOnNext(repository::setLanguageDirection));
    }

    @Override
    public Completable setDst(LanguageCode code) {
        return Completable.fromObservable(
                repository.getSrcLanguage()
                        .subscribeOn(ioScheduler)
                        .zipWith(repository.getDstLanguage(), TranslateDirection<LanguageCode>::new)
                        .take(1)
                        .map(oldDirection -> normalize(new TranslateDirection<>(oldDirection.getSrc(), code), oldDirection))
                        .doOnNext(repository::setLanguageDirection));
    }

    @Override
    public Completable swap() {
        return Completable.fromObservable(
                repository.getSrcLanguage()
                        .subscribeOn(ioScheduler)
                        .zipWith(repository.getDstLanguage(), (src, dst) -> new TranslateDirection<>(dst, src))
                        .take(1)
                        .doOnNext(repository::setLanguageDirection));
    }

    TranslateDirection<LanguageCode> normalize(TranslateDirection<LanguageCode> newDirection, TranslateDirection<LanguageCode> oldDirection) {
        if (newDirection.getSrc() == oldDirection.getDst()) {
            // swap
            return new TranslateDirection<>(newDirection.getSrc(), oldDirection.getSrc());
        } else if (newDirection.getDst() == oldDirection.getSrc()) {
            // swap
            return new TranslateDirection<>(oldDirection.getDst(), newDirection.getSrc());
        }
        return new TranslateDirection<>(newDirection.getSrc(), newDirection.getDst());
    }
}
