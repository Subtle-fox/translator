package com.andyanika.usecases;

import com.andyanika.translator.common.LocalRepository;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRowModel;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class HistoryUseCase implements Usecase<String, List<TranslationRowModel>> {
    private LocalRepository repository;

    @Inject
    public HistoryUseCase(LocalRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TranslationRowModel> run(String filter) {
        List<TranslationRowModel> historyList = repository.getHistory();
        if (filter == null || filter.isEmpty()) {
            return historyList;
        }

        ArrayList<TranslationRowModel> filtered = new ArrayList<>();
        for (TranslationRowModel tr : historyList) {
            if (tr.translateResult.textSrc.contains(filter) || tr.translateResult.textTranslated.contains(filter)) {
                filtered.add(tr);
            }
        }
        return filtered;
    }
}
