package com.andyanika.translator.repository.local

import android.content.SharedPreferences
import com.andyanika.translator.common.interfaces.LocalRepository
import com.andyanika.translator.common.models.LanguageCode
import com.andyanika.translator.common.models.TranslateDirection
import com.andyanika.translator.common.models.TranslateRequest
import com.andyanika.translator.common.models.TranslateResult
import com.andyanika.translator.repository.local.model.FavoriteModel
import com.andyanika.translator.repository.local.model.WordFavoriteModel
import com.andyanika.translator.repository.local.model.WordModel
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.Callable

internal class LocalRepositoryImpl(
    private val dao: TranslatorDao,
    private val preferences: SharedPreferences,
    private val adapter: ModelsAdapter,
    private val ioScheduler: Scheduler
) : LocalRepository {
    private val srcLanguageSubject = PublishSubject.create<LanguageCode>()
    private val dstLanguageSubject = PublishSubject.create<LanguageCode>()
    override fun getHistory(): Flowable<List<com.andyanika.translator.common.models.FavoriteModel>> {
        return Flowable.fromPublisher(dao.history)
            .flatMap { list: List<WordFavoriteModel?>? ->
                Flowable.fromIterable(list)
                    .map { dbModel: WordFavoriteModel? ->
                        adapter.toTranslationRowModel(
                            dbModel!!
                        )
                    }.toList()
                    .toFlowable()
            }
    }

    override fun getFavorites(): Flowable<List<com.andyanika.translator.common.models.FavoriteModel>> {
        return Flowable.fromPublisher(dao.favorites)
            .flatMap { list: List<WordModel?>? ->
                Flowable.fromIterable(list)
                    .map { dbModel: WordModel? ->
                        adapter.toTranslationRowModel(
                            dbModel!!
                        )
                    }.toList()
                    .toFlowable()
            }
    }

    override fun translate(request: TranslateRequest): Single<TranslateResult> {
        val callable = Callable {
            val wordModel =
                dao.getTranslation(request.text, request.direction.src.toString(), request.direction.dst.toString())
            adapter.toTranslationResult(wordModel!!)
        }
        return Single.fromCallable(callable)
    }

    override fun addTranslation(translateResult: TranslateResult): Single<TranslateResult> {
        val model = adapter.toWordModel(translateResult)
        return Completable.fromAction { dao.addTranslation(model) }
            .subscribeOn(ioScheduler)
            .toSingleDefault(translateResult)
            .doOnSuccess { r: TranslateResult? -> Timber.d("saved to local db OK: %s", r) }
            .doOnError { e: Throwable? -> Timber.e(e, "saved to local db exception") }
    }

    override fun addFavorites(wordId: Int) {
        val model = FavoriteModel(wordId)
        dao.addFavorite(model)
    }

    override fun removeFavorite(wordId: Int) {
        val model = FavoriteModel(wordId)
        dao.removeFavorite(model)
    }

    override fun getSrcLanguage(): Observable<LanguageCode> {
        return srcLanguageSubject
            .startWith(Observable.fromCallable {
                val s = preferences.getString(LANGUAGE_SRC, null)
                LanguageCode.tryParse(s, LanguageCode.RU)
            })
            .onErrorReturnItem(LanguageCode.RU)
            .subscribeOn(ioScheduler)
    }

    override fun getDstLanguage(): Observable<LanguageCode> {
        return dstLanguageSubject
            .startWith(Observable.fromCallable {
                val s = preferences.getString(LANGUAGE_DST, null)
                LanguageCode.tryParse(s, LanguageCode.EN)
            })
            .onErrorReturnItem(LanguageCode.EN)
            .subscribeOn(ioScheduler)
    }

    override fun setLanguageDirection(direction: TranslateDirection<LanguageCode>) {
        Timber.d("save repository direction: %s - %s", direction.src, direction.dst)
        preferences
            .edit()
            .putString("language_src", direction.src.toString())
            .putString("language_dst", direction.dst.toString())
            .apply()
        srcLanguageSubject.onNext(direction.src)
        dstLanguageSubject.onNext(direction.dst)
    }

    override fun getAvailableLanguages(): Observable<LanguageCode> {
        return Observable.fromArray(*LanguageCode.values()).cache()
    }

    companion object {
        const val LANGUAGE_SRC = "language_src"
        const val LANGUAGE_DST = "language_dst"
    }
}
