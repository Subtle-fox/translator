package com.andyanika.usecases;

import com.andyanika.translator.common.interfaces.LocalRepository;
import com.andyanika.translator.common.interfaces.Resources;
import com.andyanika.translator.common.interfaces.usecase.GetLanguagesUseCase;
import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.LanguageDescription;
import com.andyanika.translator.common.models.UiLanguageModel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class GetLanguagesUseCaseImpl implements GetLanguagesUseCase {
    private final Resources resources;
    private final LocalRepository repository;
    private final Scheduler ioScheduler;

    @Inject
    public GetLanguagesUseCaseImpl(Resources resources, LocalRepository repository, @Named("io") Scheduler ioScheduler) {
        this.resources = resources;
        this.repository = repository;
        this.ioScheduler = ioScheduler;
    }

    @Override
    public Observable<List<UiLanguageModel>> run(boolean selectSource) {
        Observable<LanguageCode> selectedLanguage =
                selectSource
                        ? repository.getSrcLanguage()
                        : repository.getDstLanguage();

        Observable<LanguageDescription> availableLanguages = repository
                .getAvailableLanguagesObservable()
                .map(code -> new LanguageDescription(code, resources.getString("lang_" + code.toString().toLowerCase())));

        Observable<UiLanguageModel> combineLatest = selectedLanguage
                .take(1)
                .flatMap(code -> availableLanguages
                        .map(desc -> new UiLanguageModel(desc.code, desc.description, desc.code == code))
                );


        return combineLatest
                .buffer(100)
                .subscribeOn(ioScheduler);
    }
}
