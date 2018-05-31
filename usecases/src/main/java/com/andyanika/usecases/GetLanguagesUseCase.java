package com.andyanika.usecases;

import com.andyanika.translator.common.LocalRepository;
import com.andyanika.translator.common.Resources;
import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.TranslateDirection;
import com.andyanika.translator.common.models.LanguageRowModel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;

public class GetLanguagesUseCase {
    private final Resources resources;
    private final LocalRepository repository;
    private final Scheduler ioScheduler;

    @Inject
    public GetLanguagesUseCase(Resources resources, LocalRepository repository, @Named("io") Scheduler ioScheduler) {
        this.resources = resources;
        this.repository = repository;
        this.ioScheduler = ioScheduler;
    }

    public Single<List<LanguageRowModel>> run(boolean selectSource) {
        TranslateDirection translateDirection = repository.getLanguageDirection();
        LanguageCode current = selectSource ? translateDirection.src : translateDirection.dst;

        Observable<LanguageCode> availableLanguageCodes = Observable.fromIterable(repository.getAvailableLanguages());
        Observable<String> names = availableLanguageCodes.map(x -> resources.getString("lang_" + x.toString().toLowerCase()));

        return availableLanguageCodes
                .zipWith(names, (code, name) -> new LanguageRowModel(code, name, code == current))
                .toList()
                .subscribeOn(ioScheduler);
    }
}
