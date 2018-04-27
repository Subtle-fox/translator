package com.andyanika.datasource.local;

import com.andyanika.translator.common.LocalRepository;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRequest;

import java.util.ArrayList;
import java.util.List;

class LocalRepositoryImpl implements LocalRepository {
    @Override
    public List<TranslateResult> getHistory() {
        List<TranslateResult> data = new ArrayList<>();
        data.add(new TranslateResult("word 1", "[en] translated word 1"));
        data.add(new TranslateResult("word 2", "[en] translated word 2"));
        data.add(new TranslateResult("word 3", "[en] translated word 3"));
        return data;
    }

    @Override
    public List<TranslateResult> getFavorites() {
        List<TranslateResult> data = new ArrayList<>();
        data.add(new TranslateResult("favorite 1", "[en] translated word 1"));
        data.add(new TranslateResult("favorite 2", "[en] translated word 2"));
        data.add(new TranslateResult("favorite 3", "[en] translated word 3"));
        return data;
    }

    @Override
    public TranslateResult translate(TranslationRequest request) {
        return null;
    }
}