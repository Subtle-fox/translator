package com.andyanika.usecases;

import com.andyanika.translator.common.LocalRepository;
import com.andyanika.translator.common.Resources;
import com.andyanika.translator.common.models.LanguageDescription;
import com.andyanika.translator.common.models.TranslateDirection;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetSelectedLanguagesUseCase {
    private Resources resources;
    private final LocalRepository repository;

    @Inject
    public GetSelectedLanguagesUseCase(Resources resources, LocalRepository repository) {
        this.resources = resources;
        this.repository = repository;
    }

    public Observable<LanguageDescription> run() {
        TranslateDirection direction = repository.getLanguageDirection();
        return Observable.fromIterable(repository.getAvailableLanguages())
                .filter(l -> l == direction.src || l == direction.dst)
                .map(languageCode -> {
                    String name = resources.getString("lang_" + languageCode.toString().toLowerCase());
                    return new LanguageDescription(languageCode, name, languageCode == direction.src);
                });
    }
}
