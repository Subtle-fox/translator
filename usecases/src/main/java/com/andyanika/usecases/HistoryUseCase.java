package com.andyanika.usecases;

import com.andyanika.translator.common.LocalRepository;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRowModel;

import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class HistoryUseCase {
    private LocalRepository repository;

    @Inject
    public HistoryUseCase(LocalRepository repository) {
        this.repository = repository;
    }

    public Flowable<List<TranslationRowModel>> run(String filter) {
        return repository
                .getHistory()
                .flatMap(list -> Flowable.fromIterable(list)
                        .filter(item -> filter(item.translateResult, filter)).toList()
                        .toFlowable());
    }

    private boolean filter(TranslateResult result, String filter) {
        // TODO: 31.05.2018: implement via Room query
        return filter == null
                || filter.isEmpty()
                || result.textSrc.contains(filter)
                || result.textTranslated.contains(filter);
    }
}
