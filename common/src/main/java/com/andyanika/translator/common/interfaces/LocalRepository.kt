package com.andyanika.translator.common.interfaces

import com.andyanika.translator.common.models.*
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    suspend fun translate(request: TranslateRequest): TranslateResult?
    suspend fun addTranslation(translateResult: TranslateResult)
    fun getHistory(): Flow<List<FavoriteModel>>
    fun getFavorites(): Flow<List<FavoriteModel>>
    suspend fun addFavorites(wordId: Int)
    suspend fun removeFavorite(wordId: Int)
    suspend fun setLanguageDirection(direction: TranslateDirection<LanguageCode>)
    suspend fun getAvailableLanguages(): List<LanguageCode>

    fun observeSrcLanguage(): Flow<LanguageCode>
    fun observeDstLanguage(): Flow<LanguageCode>
    suspend fun getSrcLanguage(): LanguageCode
    suspend fun getDstLanguage(): LanguageCode
}
