package com.andyanika.datasource.local;

import com.andyanika.datasource.local.model.WordFavoriteModel;
import com.andyanika.datasource.local.model.WordModel;
import com.andyanika.translator.common.models.FavoriteModel;
import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.TranslateDirection;
import com.andyanika.translator.common.models.TranslateResult;

class ModelsAdapter {
    TranslateResult toTranslationResult(WordModel dbModel) {
        TranslateDirection<LanguageCode> direction = new TranslateDirection<>(LanguageCode.valueOf(dbModel.languageSrc), LanguageCode.valueOf(dbModel.languageDst));
        return new TranslateResult(dbModel.textSrc, dbModel.textDst, direction);
    }

    FavoriteModel toTranslationRowModel(WordModel dbModel) {
        TranslateResult translateResult = toTranslationResult(dbModel);
        return new FavoriteModel(dbModel.id, translateResult, true);
    }

    FavoriteModel toTranslationRowModel(WordFavoriteModel dbModel) {
        TranslateDirection<LanguageCode> direction = new TranslateDirection<>(LanguageCode.valueOf(dbModel.languageSrc), LanguageCode.valueOf(dbModel.languageDst));
        TranslateResult translateResult = new TranslateResult(dbModel.textSrc, dbModel.textDst, direction);
        return new FavoriteModel(dbModel.id, translateResult, dbModel.favoriteId > 0);
    }

    WordModel toWordModel(TranslateResult translateResult) {
        return new WordModel(translateResult.textSrc, translateResult.textDst, translateResult.direction.src.toString(), translateResult.direction.dst.toString());
    }
}
