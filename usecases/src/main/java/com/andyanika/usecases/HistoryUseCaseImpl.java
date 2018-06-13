package com.andyanika.usecases;

import com.andyanika.translator.common.interfaces.LocalRepository;
import com.andyanika.translator.common.interfaces.usecase.HistoryUseCase;
import com.andyanika.translator.common.models.FavoriteModel;
import com.andyanika.translator.common.models.TranslateResult;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

class HistoryUseCaseImpl implements HistoryUseCase {
    private final LocalRepository repository;
    private final Scheduler ioScheduler;

    @Inject
    HistoryUseCaseImpl(LocalRepository repository, @Named("io") Scheduler ioScheduler) {
        this.repository = repository;
        this.ioScheduler = ioScheduler;
    }

    @Override
    public Flowable<List<FavoriteModel>> run(String filter, int limit) {
        return repository
                .getHistory()
                .subscribeOn(ioScheduler)
                .take(limit)
                .flatMap(list -> Flowable.fromIterable(list)
                        .filter(item -> filter(item.translateResult, filter)).toList()
                        .toFlowable());
    }

    private boolean filter(TranslateResult result, String filter) {
        // TODO: 31.05.2018: implement via Room query
        return filter == null
                || filter.isEmpty()
                || result.textSrc.contains(filter)
                || result.textDst.contains(filter);
    }
}
