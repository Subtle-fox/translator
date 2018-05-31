package com.andyanika.usecases;

import com.andyanika.translator.common.DirectionPair;
import com.andyanika.translator.common.LocalRepository;
import com.andyanika.translator.common.Resources;
import com.andyanika.translator.common.models.LanguageCode;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class GetSelectedLanguagesUseCase {
    private final LocalRepository repository;
    private final Function<LanguageCode, String> toUiStringFunction;

    @Inject
    public GetSelectedLanguagesUseCase(Resources resources, LocalRepository repository) {
        this.repository = repository;
        this.toUiStringFunction = code -> resources.getString("lang_" + code.toString().toLowerCase());
    }

    public Observable<DirectionPair<String>> run() {
        Observable<String> srcLanguage = repository.getSrcLanguage().map(toUiStringFunction);
        Observable<String> dstLanguage = repository.getDstLanguage().map(toUiStringFunction);
        return srcLanguage.zipWith(dstLanguage, DirectionPair::new);
    }
}
