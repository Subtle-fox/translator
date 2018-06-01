package com.andyanika.usecases;

import com.andyanika.translator.common.LocalRepository;
import com.andyanika.translator.common.Resources;
import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.LanguageDescription;
import com.andyanika.translator.common.models.LanguageRowModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    public Observable<List<LanguageRowModel>> run(boolean selectSource) {
        Observable<LanguageCode> selectedLanguage;
        if (selectSource) {
            selectedLanguage = repository.getSrcLanguage();
        } else {
            selectedLanguage = repository.getDstLanguage();
        }

        Observable<LanguageDescription> availableLanguages = repository
                .getAvailableLanguagesObservable()
                .map(x -> new LanguageDescription(x, resources.getString("lang_" + x.toString().toLowerCase()), true))
                .doOnNext(s -> System.out.println("available"))
                .doOnComplete(() -> System.out.println("available completed"));
        ;

        Observable<LanguageRowModel> combineLatest =
                availableLanguages.withLatestFrom(selectedLanguage, (d, code) -> new LanguageRowModel(d.code, d.description, d.code == code))
                        .doOnNext(x -> System.out.println("combined"))
                        .doOnComplete(() -> System.out.println("combine completed"));

        return combineLatest
                .buffer(1, TimeUnit.SECONDS)
                .doOnNext(l -> System.out.println("list size " + l.size()))
                .subscribeOn(ioScheduler);
    }
}
