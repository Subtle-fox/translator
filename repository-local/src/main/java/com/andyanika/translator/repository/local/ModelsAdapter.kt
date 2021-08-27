package com.andyanika.translator.repository.local

import core.models.FavoriteModel
import core.models.LanguageCode
import core.models.TranslateDirection
import core.models.TranslateResult
import com.andyanika.translator.repository.local.entity.WordFavoriteEntity
import com.andyanika.translator.repository.local.entity.WordEntity

class ModelsAdapter {
    fun toTranslationResult(dbEntity: WordEntity): TranslateResult {
        val direction =
            TranslateDirection(LanguageCode.valueOf(dbEntity.languageSrc), LanguageCode.valueOf(dbEntity.languageDst))
        return TranslateResult(dbEntity.textSrc, dbEntity.textDst, direction)
    }

    fun toTranslationRowModel(dbEntity: WordEntity): FavoriteModel {
        val translateResult = toTranslationResult(dbEntity)
        return FavoriteModel(dbEntity.id, translateResult, true)
    }

    fun toTranslationRowModel(dbEntity: WordFavoriteEntity): FavoriteModel {
        val direction =
            TranslateDirection(LanguageCode.valueOf(dbEntity.languageSrc), LanguageCode.valueOf(dbEntity.languageDst))
        val translateResult = TranslateResult(dbEntity.textSrc, dbEntity.textDst, direction)
        return FavoriteModel(dbEntity.id, translateResult, dbEntity.favoriteId > 0)
    }

    fun toWordModel(translateResult: TranslateResult): WordEntity {
        return WordEntity(
            translateResult.textSrc,
            translateResult.textDst,
            translateResult.direction.src.toString(),
            translateResult.direction.dst.toString()
        )
    }
}
