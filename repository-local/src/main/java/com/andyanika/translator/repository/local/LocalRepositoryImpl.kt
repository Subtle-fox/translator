package com.andyanika.translator.repository.local

import android.content.SharedPreferences
import com.andyanika.translator.common.interfaces.LocalRepository
import com.andyanika.translator.common.models.*
import com.andyanika.translator.repository.local.entity.FavoriteEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import timber.log.Timber

internal class LocalRepositoryImpl(
    private val dao: TranslatorDao,
    private val preferences: SharedPreferences,
    private val adapter: ModelsAdapter,
    private val ioDispatcher: CoroutineDispatcher,
) : LocalRepository {

    private val srcLanguageState = MutableSharedFlow<LanguageCode>()
    private val dstLanguageState = MutableSharedFlow<LanguageCode>()

    override fun getHistory(): Flow<List<FavoriteModel>> {
        return dao
            .getHistory()
            .map { models -> models.map(adapter::toTranslationRowModel) }
    }

    override fun getFavorites(): Flow<List<FavoriteModel>> {
        return dao
            .getFavorites()
            .map { models -> models.map(adapter::toTranslationRowModel) }
    }

    override suspend fun translate(request: TranslateRequest): TranslateResult? {
        return withContext(ioDispatcher) {
            dao.getTranslation(
                text = request.text,
                langSrc = request.direction.src.toString(),
                langDst = request.direction.dst.toString()
            )?.let(adapter::toTranslationResult)
        }
    }

    override suspend fun addTranslation(translateResult: TranslateResult) {
        return withContext(ioDispatcher) {
            val model = adapter.toWordModel(translateResult)
            dao.addTranslation(model)
        }
    }

    override suspend fun addFavorites(wordId: Int) {
        withContext(ioDispatcher) {
            val entity = FavoriteEntity(wordId)
            dao.addFavorite(entity)
        }
    }

    override suspend fun removeFavorite(wordId: Int) {
        withContext(ioDispatcher) {
            val entity = FavoriteEntity(wordId)
            dao.removeFavorite(entity)
        }
    }

    override fun observeSrcLanguage(): Flow<LanguageCode> {
        return srcLanguageState
            .onStart { getSrcLanguage() }
            .catch { emit(LanguageCode.RU) }
            .flowOn(ioDispatcher)
    }

    override fun observeDstLanguage(): Flow<LanguageCode> {
        return dstLanguageState
            .onStart { getDstLanguage() }
            .flowOn(ioDispatcher)
    }

    override suspend fun getSrcLanguage(): LanguageCode {
        val s = preferences.getString(LANGUAGE_SRC, null)
        return LanguageCode.tryParse(s, LanguageCode.RU)
    }

    override suspend fun getDstLanguage(): LanguageCode {
        val s = preferences.getString(LANGUAGE_DST, null)
        return LanguageCode.tryParse(s, LanguageCode.EN)
    }

    override suspend fun setLanguageDirection(direction: TranslateDirection<LanguageCode>) {
        Timber.d("save repository direction: %s - %s", direction.src, direction.dst)
        preferences
            .edit()
            .putString("language_src", direction.src.toString())
            .putString("language_dst", direction.dst.toString())
            .apply()
        srcLanguageState.emit(direction.src)
        dstLanguageState.emit(direction.dst)
    }

    override suspend fun getAvailableLanguages(): List<LanguageCode> {
        return LanguageCode.values().toList()
    }

    companion object {
        const val LANGUAGE_SRC = "language_src"
        const val LANGUAGE_DST = "language_dst"
    }
}
