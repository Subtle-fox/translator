package com.andyanika.usecases;

import com.andyanika.translator.common.LocalRepository;
import com.andyanika.translator.common.models.TranslateResult;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class HistoryUseCase implements Usecase<String, List<TranslateResult>> {
    private LocalRepository repository;

    @Inject
    public HistoryUseCase(LocalRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TranslateResult> run(String filter) {
        List<TranslateResult> historyList = repository.getHistory();
        if (filter == null || filter.isEmpty()) {
            return historyList;
        }

        ArrayList<TranslateResult> filtered = new ArrayList<>();
        for (TranslateResult tr : historyList) {
            if (tr.textSrc.contains(filter) || tr.textTranslated.contains(filter)) {
                filtered.add(tr);
            }
        }
        return filtered;
    }
}
