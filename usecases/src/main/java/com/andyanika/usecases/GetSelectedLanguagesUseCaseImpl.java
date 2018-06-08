package com.andyanika.usecases;

import com.andyanika.translator.common.interfaces.LocalRepository;
import com.andyanika.translator.common.interfaces.Resources;
import com.andyanika.translator.common.interfaces.usecase.GetSelectedLanguageUseCase;
import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.TranslateDirection;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;

class GetSelectedLanguagesUseCaseImpl implements GetSelectedLanguageUseCase {
    private final LocalRepository repository;
    private final Function<LanguageCode, String> toUiStringFunction;
    private final Scheduler ioScheduler;

    @Inject
    GetSelectedLanguagesUseCaseImpl(Resources resources, LocalRepository repository, @Named("io") Scheduler ioScheduler) {
        this.repository = repository;
        this.toUiStringFunction = code -> resources.getString("lang_" + code.toString().toLowerCase());
        this.ioScheduler = ioScheduler;
    }

    @Override
    public Observable<TranslateDirection<String>> run() {
        Observable<String> srcLanguage = repository.getSrcLanguage().map(toUiStringFunction);
        Observable<String> dstLanguage = repository.getDstLanguage().map(toUiStringFunction);
        return srcLanguage
                .subscribeOn(ioScheduler)
                .zipWith(dstLanguage, TranslateDirection::new);
    }
}
