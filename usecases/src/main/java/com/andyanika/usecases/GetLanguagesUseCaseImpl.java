package com.andyanika.usecases;

import com.andyanika.translator.common.interfaces.LocalRepository;
import com.andyanika.translator.common.interfaces.Resources;
import com.andyanika.translator.common.interfaces.usecase.GetLanguagesUseCase;
import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.LanguageDescription;
import com.andyanika.translator.common.models.ui.DisplayLanguageModel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import timber.log.Timber;

class GetLanguagesUseCaseImpl implements GetLanguagesUseCase {
    private final Resources resources;
    private final LocalRepository repository;
    private final Scheduler ioScheduler;

    @Inject
    GetLanguagesUseCaseImpl(Resources resources, LocalRepository repository, @Named("io") Scheduler ioScheduler) {
        this.resources = resources;
        this.repository = repository;
        this.ioScheduler = ioScheduler;
    }

    @Override
    public Observable<List<DisplayLanguageModel>> run(boolean selectSource) {
        Observable<LanguageCode> selectedLanguage = (selectSource
                ? repository.getSrcLanguage()
                : repository.getDstLanguage())
                .doOnNext(l -> Timber.d("onNext: selected lang %s", l));

        Observable<LanguageDescription> availableLanguages = repository
                .getAvailableLanguages().doOnNext(l -> Timber.d("onNext: available lang %s", l))
                .map(code -> new LanguageDescription(code, resources.getString("lang_" + code.toString().toLowerCase())));

        Observable<DisplayLanguageModel> combineLatest = selectedLanguage
                .take(1)
                .flatMap(code -> availableLanguages
                        .map(desc -> new DisplayLanguageModel(desc.code, desc.description, desc.code == code))
                        .doOnNext(desc -> Timber.d("onMapped: lang %s", desc.description))
                );


        return combineLatest
                .subscribeOn(ioScheduler)
                .buffer(100)
                .doOnNext(list -> Timber.d("onNext: list of %d elements", list.size()));
    }
}
