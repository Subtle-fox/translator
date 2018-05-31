package com.andyanika.usecases;

import com.andyanika.translator.common.LocalRepository;
import com.andyanika.translator.common.Resources;
import com.andyanika.translator.common.models.LanguageDescription;
import com.andyanika.translator.common.models.TranslateDirection;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class GetSelectedLanguagesUseCase {
    private final Resources resources;
    private final LocalRepository repository;
    private final Scheduler ioScheduler;

    @Inject
    public GetSelectedLanguagesUseCase(Resources resources, LocalRepository repository, @Named("io") Scheduler ioScheduler) {
        this.resources = resources;
        this.repository = repository;
        this.ioScheduler = ioScheduler;
    }

    public Observable<LanguageDescription> run() {
        TranslateDirection direction = repository.getLanguageDirection();
        return Observable
                .fromIterable(repository.getAvailableLanguages())
                .subscribeOn(ioScheduler)
                .filter(l -> l == direction.src || l == direction.dst)
                .map(languageCode -> {
                    String name = resources.getString("lang_" + languageCode.toString().toLowerCase());
                    return new LanguageDescription(languageCode, name, languageCode == direction.src);
                });
    }
}
