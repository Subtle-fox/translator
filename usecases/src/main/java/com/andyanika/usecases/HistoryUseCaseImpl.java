package com.andyanika.usecases;

import com.andyanika.translator.common.interfaces.LocalRepository;
import com.andyanika.translator.common.interfaces.usecase.HistoryUseCase;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.UiTranslationModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class HistoryUseCaseImpl implements HistoryUseCase {
    private LocalRepository repository;

    @Inject
    public HistoryUseCaseImpl(LocalRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flowable<List<UiTranslationModel>> run(String filter, int limit) {
        return repository
                .getHistory()
                .take(limit)
                .flatMap(list -> Flowable.fromIterable(list)
                        .filter(item -> filter(item.translateResult, filter)).toList()
                        .toFlowable());
    }

    private boolean filter(TranslateResult result, String filter) {
        // TODO: 31.05.2018: implement via Room query
        // runs in background thread by default
        return filter == null
                || filter.isEmpty()
                || result.textSrc.contains(filter)
                || result.textTranslated.contains(filter);
    }
}
