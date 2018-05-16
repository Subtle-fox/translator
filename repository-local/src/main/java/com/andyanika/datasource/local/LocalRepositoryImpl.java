package com.andyanika.datasource.local;

import com.andyanika.datasource.local.model.FavoriteModel;
import com.andyanika.datasource.local.model.WordModel;
import com.andyanika.translator.common.LocalRepository;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRequest;
import com.andyanika.translator.common.models.TranslationRowModel;

import java.util.List;

import io.reactivex.Flowable;

class LocalRepositoryImpl implements LocalRepository {
    private TranslatorDao dao;
    private ModelsAdapter adapter;

    LocalRepositoryImpl(TranslatorDao dao, ModelsAdapter adapter) {
        this.dao = dao;
        this.adapter = adapter;
    }

    @Override
    public Flowable<List<TranslationRowModel>> getHistory() {
        return dao.getHistory()
                .flatMap(list -> Flowable.fromIterable(list)
                        .map(item -> adapter.convertToRowModel(item)).toList()
                        .toFlowable());
    }

    @Override
    public Flowable<List<TranslationRowModel>> getFavorites() {
        return dao.getFavorites()
                .flatMap(list -> Flowable.fromIterable(list)
                        .map(item -> adapter.convertToRowModel(item)).toList()
                        .toFlowable());
    }

    @Override
    public TranslateResult translate(TranslationRequest request) {
        WordModel result = dao.getTranslation(request.text, request.languageSrc.toString(), request.languageDst.toString());
        return result == null ? null : adapter.convert(result);
    }

    @Override
    public long addTranslation(TranslateResult translateResult) {
        WordModel model = adapter.convert(translateResult);
        return dao.addTranslation(model);
    }

    @Override
    public void addFavorites(int wordId) {
        FavoriteModel model = new FavoriteModel(wordId);
        dao.addFavorite(model);
    }

    @Override
    public void removeFavorite(int wordId) {
        FavoriteModel model = new FavoriteModel(wordId);
        dao.removeFavorite(model);
    }
}