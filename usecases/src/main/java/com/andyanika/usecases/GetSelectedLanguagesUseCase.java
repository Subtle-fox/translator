package com.andyanika.usecases;

import com.andyanika.translator.common.LocalRepository;
import com.andyanika.translator.common.Resources;
import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.LanguageDescription;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

public class GetSelectedLanguagesUseCase {
    private Resources resources;
    private final LocalRepository repository;

    @Inject
    public GetSelectedLanguagesUseCase(Resources resources, LocalRepository repository) {
        this.resources = resources;
        this.repository = repository;
    }

    public Single<LanguageDescription> getSrc() {
        return run(repository.getLanguageDirection().src);
    }

    public Single<LanguageDescription> getDst() {
        return run(repository.getLanguageDirection().dst);
    }

    Single<LanguageDescription> run(LanguageCode languageCode) {
        return Observable.fromIterable(repository.getAvailableLanguages())
                .filter(code -> code == languageCode)
                .single(LanguageCode.RU)
                .map(x -> {
                    String name = resources.getString("lang_" + x.toString().toLowerCase());
                    return new LanguageDescription(x, name);
                });
    }
}
