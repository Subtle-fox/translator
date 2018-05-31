package com.andyanika.datasource.local;

import android.content.SharedPreferences;

import com.andyanika.datasource.local.model.FavoriteModel;
import com.andyanika.datasource.local.model.WordModel;
import com.andyanika.translator.common.LocalRepository;
import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.TranslateDirection;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRequest;
import com.andyanika.translator.common.models.TranslationRowModel;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;

class LocalRepositoryImpl implements LocalRepository {
    private TranslatorDao dao;
    private SharedPreferences preferences;
    private ModelsAdapter adapter;
    private Scheduler ioScheduler;

    LocalRepositoryImpl(TranslatorDao dao, SharedPreferences preferences, ModelsAdapter adapter, Scheduler ioScheduler) {
        this.dao = dao;
        this.preferences = preferences;
        this.adapter = adapter;
        this.ioScheduler = ioScheduler;
    }

    @Override
    public Flowable<List<TranslationRowModel>> getHistory() {
        return dao.getHistory()
                .flatMap(list -> Flowable.fromIterable(list)
                        .map(item -> adapter.toTranslationRowModel(item)).toList()
                        .toFlowable());
    }

    @Override
    public Flowable<List<TranslationRowModel>> getFavorites() {
        return dao.getFavorites()
                .flatMap(list -> Flowable.fromIterable(list)
                        .map(item -> adapter.toTranslationRowModel(item)).toList()
                        .toFlowable());
    }

    @Override
    public Single<TranslateResult> translate(TranslationRequest request) {
        return dao.getTranslation(request.text, request.direction.src.toString(), request.direction.dst.toString())
                .map(adapter::toTranslationResult);
    }

    @Override
    public void addTranslation(TranslateResult translateResult) {
        WordModel model = adapter.toWordModel(translateResult);
        dao.addTranslation(model);
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

    @Override
    public TranslateDirection getLanguageDirection() {
        String langSrc = preferences.getString("language_src", null);
        String langDst = preferences.getString("language_dst", null);
        return new TranslateDirection(LanguageCode.tryParse(langSrc, LanguageCode.RU), LanguageCode.tryParse(langDst, LanguageCode.EN));
    }

    PublishSubject<LanguageCode> subjectSrc = PublishSubject.create();
    PublishSubject<LanguageCode> subjectDst = PublishSubject.create();

    @Override
    public Observable<LanguageCode> getSrcLanguage() {
        return subjectSrc
                .startWith(Observable.fromCallable(() -> {
                            String s = preferences.getString("language_src", null);
                            return LanguageCode.tryParse(s, LanguageCode.RU);
                        }
                ))
                .subscribeOn(ioScheduler);
    }

    @Override
    public Observable<LanguageCode> getDstLanguage() {
        return subjectSrc
                .startWith(Observable.fromCallable(() -> {
                            String s = preferences.getString("language_dst", null);
                            return LanguageCode.tryParse(s, LanguageCode.EN);
                        }
                ))
                .subscribeOn(ioScheduler);
    }


    @Override
    public void setLanguageDirection(TranslateDirection direction) {
        preferences
                .edit()
                .putString("language_src", direction.src.toString())
                .putString("language_dst", direction.dst.toString())
                .apply();

        subjectSrc.onNext(direction.src);
        subjectDst.onNext(direction.dst);
    }

    @Override
    public List<LanguageCode> getAvailableLanguages() {
        // TODO: somehow fetched/ stored / cached
        return Arrays.asList(LanguageCode.values());
    }
}