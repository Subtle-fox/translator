package com.andyanika.datasource.local;

import com.andyanika.datasource.local.model.FavoriteModel;
import com.andyanika.datasource.local.model.WordFavoriteModel;
import com.andyanika.datasource.local.model.WordModel;
import com.andyanika.translator.common.LocalRepository;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRequest;
import com.andyanika.translator.common.models.TranslationRowModel;

import java.util.ArrayList;
import java.util.List;

class LocalRepositoryImpl implements LocalRepository {
    private TranslatorDao dao;
    private ModelsAdapter adapter;

    LocalRepositoryImpl(TranslatorDao dao, ModelsAdapter adapter) {
        this.dao = dao;
        this.adapter = adapter;
    }

    @Override
    public List<TranslationRowModel> getHistory() {
        List<WordFavoriteModel> history = dao.getHistory();
        List<TranslationRowModel> result = new ArrayList<>();
        if (history != null) {
            for (WordFavoriteModel wordModel : history) {
                result.add(adapter.convertToRowModel(wordModel));
            }
        }
        return result;
    }

    @Override
    public List<TranslationRowModel> getFavorites() {
        List<WordModel> history = dao.getFavorites();
        List<TranslationRowModel> result = new ArrayList<>();
        if (history != null) {
            for (WordModel wordModel : history) {
                result.add(adapter.convertToRowModel(wordModel));
            }
        }
        return result;
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