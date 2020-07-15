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
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.subjects.PublishSubject;
import timber.log.Timber;

class LocalRepositoryImpl implements LocalRepository {
    static final String LANGUAGE_SRC = "language_src";
    static final String LANGUAGE_DST = "language_dst";


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
        return Flowable.fromPublisher(dao.getHistory())
                .flatMap(list -> Flowable.fromIterable(list)
                        .map(adapter::toTranslationRowModel).toList()
                        .toFlowable());
    }

    @Override
    public Flowable<List<FavoriteModel>> getFavorites() {
        return Flowable.fromPublisher(dao.getFavorites())
                .flatMap(list -> Flowable.fromIterable(list)
                        .map(adapter::toTranslationRowModel).toList()
                        .toFlowable());
    }

    @Override
    public Single<TranslateResult> translate(TranslateRequest request) {
        Callable<TranslateResult> callable = () -> {
            WordModel wordModel = dao.getTranslation(request.getText(), request.getDirection().getSrc().toString(), request.getDirection().getDst().toString());
            return adapter.toTranslationResult(wordModel);
        };
        return Single.fromCallable(callable);
    }

    @Override
    public Single<TranslateResult> addTranslation(TranslateResult translateResult) {
        WordModel model = adapter.toWordModel(translateResult);
        return Completable.fromAction(() -> dao.addTranslation(model))
                .subscribeOn(ioScheduler)
                .toSingleDefault(translateResult)
                .doOnSuccess(r -> Timber.d("saved to local db OK: %s", r))
                .doOnError(e -> Timber.e(e, "saved to local db exception"));
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
                            String s = preferences.getString(LANGUAGE_SRC, null);
                            return LanguageCode.tryParse(s, LanguageCode.RU);
                        }
                ))
                .onErrorReturnItem(LanguageCode.RU)
                .subscribeOn(ioScheduler);
    }

    @Override
    public Observable<LanguageCode> getDstLanguage() {
        return dstLanguageSubject
                .startWith(Observable.fromCallable(() -> {
                            String s = preferences.getString(LANGUAGE_DST, null);
                            return LanguageCode.tryParse(s, LanguageCode.EN);
                        }
                ))
                .onErrorReturnItem(LanguageCode.EN)
                .subscribeOn(ioScheduler);
    }


    @Override
    public void setLanguageDirection(TranslateDirection<LanguageCode> direction) {
        Timber.d("save repository direction: %s - %s", direction.getSrc(), direction.getDst());
        preferences
                .edit()
                .putString("language_src", direction.getSrc().toString())
                .putString("language_dst", direction.getDst().toString())
                .apply();

        srcLanguageSubject.onNext(direction.getSrc());
        dstLanguageSubject.onNext(direction.getDst());
    }

    @Override
    public Observable<LanguageCode> getAvailableLanguages() {
        return Observable.fromArray(LanguageCode.values()).cache();
    }
}
