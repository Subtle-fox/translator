package com.andyanika.usecases;

import com.andyanika.translator.common.LocalRepository;
import com.andyanika.translator.common.models.TranslationRowModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class HistoryUseCase implements Usecase<String, Flowable<List<TranslationRowModel>>> {
    private LocalRepository repository;

    @Inject
    public HistoryUseCase(LocalRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flowable<List<TranslationRowModel>> run(String filter) {
        return repository.getHistory()
                .flatMap(list -> Flowable.fromIterable(list)
                        .filter(item -> filter(item, filter)).toList()
                        .toFlowable());
    }

    private boolean filter(TranslationRowModel tr, String filter) {
        return filter == null || filter.isEmpty() || tr.translateResult.textSrc.contains(filter) || tr.translateResult.textTranslated.contains(filter);
    }
}
