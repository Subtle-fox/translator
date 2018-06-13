package com.andyanika.translator.repository.local;

import android.content.SharedPreferences;

import com.andyanika.translator.common.interfaces.LocalRepository;
import com.andyanika.translator.common.models.FavoriteModel;
import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.TranslateDirection;
import com.andyanika.translator.common.models.TranslateRequest;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.repository.local.model.WordModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

class LocalRepositoryImpl implements LocalRepository {
    private final TranslatorDao dao;
    private final SharedPreferences preferences;
    private final ModelsAdapter adapter;
    private final Scheduler ioScheduler;

    private final PublishSubject<LanguageCode> srcLanguageSubject = PublishSubject.create();
    private final PublishSubject<LanguageCode> dstLanguageSubject = PublishSubject.create();

    LocalRepositoryImpl(TranslatorDao dao, SharedPreferences preferences, ModelsAdapter adapter, Scheduler ioScheduler) {
        this.dao = dao;
        this.preferences = preferences;
        this.adapter = adapter;
        this.ioScheduler = ioScheduler;
    }

    @Override
    public Flowable<List<FavoriteModel>> getHistory() {
        return dao.getHistory()
                .flatMap(list -> Flowable.fromIterable(list)
                        .map(adapter::toTranslationRowModel).toList()
                        .toFlowable());
    }

    @Override
    public Flowable<List<FavoriteModel>> getFavorites() {
        return dao.getFavorites()
                .flatMap(list -> Flowable.fromIterable(list)
                        .map(adapter::toTranslationRowModel).toList()
                        .toFlowable());
    }

    @Override
    public Single<TranslateResult> translate(TranslateRequest request) {
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
        com.andyanika.translator.repository.local.model.FavoriteModel model = new com.andyanika.translator.repository.local.model.FavoriteModel(wordId);
        dao.addFavorite(model);
    }

    @Override
    public void removeFavorite(int wordId) {
        com.andyanika.translator.repository.local.model.FavoriteModel model = new com.andyanika.translator.repository.local.model.FavoriteModel(wordId);
        dao.removeFavorite(model);
    }

    @Override
    public Observable<LanguageCode> getSrcLanguage() {
        return srcLanguageSubject
                .startWith(Observable.fromCallable(() -> {
                            String s = preferences.getString("language_src", null);
                            return LanguageCode.tryParse(s, LanguageCode.RU);
                        }
                ))
                .subscribeOn(ioScheduler);
    }

    @Override
    public Observable<LanguageCode> getDstLanguage() {
        return dstLanguageSubject
                .startWith(Observable.fromCallable(() -> {
                            String s = preferences.getString("language_dst", null);
                            return LanguageCode.tryParse(s, LanguageCode.EN);
                        }
                ))
                .subscribeOn(ioScheduler);
    }


    @Override
    public void setLanguageDirection(TranslateDirection<LanguageCode> direction) {
        Timber.d("save repository direction: %s - %s", direction.src, direction.dst);
        preferences
                .edit()
                .putString("language_src", direction.src.toString())
                .putString("language_dst", direction.dst.toString())
                .apply();

        srcLanguageSubject.onNext(direction.src);
        dstLanguageSubject.onNext(direction.dst);
    }

    @Override
    public Observable<LanguageCode> getAvailableLanguagesObservable() {
        return Observable.fromArray(LanguageCode.values()).cache();
    }
}