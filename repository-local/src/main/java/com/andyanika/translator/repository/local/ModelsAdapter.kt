package com.andyanika.translator.repository.local

import com.andyanika.translator.common.models.FavoriteModel
import com.andyanika.translator.common.models.LanguageCode
import com.andyanika.translator.common.models.TranslateDirection
import com.andyanika.translator.common.models.TranslateResult
import com.andyanika.translator.repository.local.model.WordFavoriteModel
import com.andyanika.translator.repository.local.model.WordModel
import javax.inject.Inject

class ModelsAdapter @Inject constructor() {
    fun toTranslationResult(dbModel: WordModel): TranslateResult {
        val direction =
            TranslateDirection(LanguageCode.valueOf(dbModel.languageSrc), LanguageCode.valueOf(dbModel.languageDst))
        return TranslateResult(dbModel.textSrc, dbModel.textDst, direction)
    }

    fun toTranslationRowModel(dbModel: WordModel): FavoriteModel {
        val translateResult = toTranslationResult(dbModel)
        return FavoriteModel(dbModel.id, translateResult, true)
    }

    fun toTranslationRowModel(dbModel: WordFavoriteModel): FavoriteModel {
        val direction =
            TranslateDirection(LanguageCode.valueOf(dbModel.languageSrc), LanguageCode.valueOf(dbModel.languageDst))
        val translateResult = TranslateResult(dbModel.textSrc, dbModel.textDst, direction)
        return FavoriteModel(dbModel.id, translateResult, dbModel.favoriteId > 0)
    }

    fun toWordModel(translateResult: TranslateResult): WordModel {
        return WordModel(
            translateResult.textSrc,
            translateResult.textDst,
            translateResult.direction.src.toString(),
            translateResult.direction.dst.toString()
        )
    }
}
