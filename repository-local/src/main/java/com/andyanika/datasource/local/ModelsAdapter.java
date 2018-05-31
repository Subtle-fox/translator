package com.andyanika.datasource.local;

import com.andyanika.datasource.local.model.WordFavoriteModel;
import com.andyanika.datasource.local.model.WordModel;
import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.TranslateDirection;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRowModel;

public class ModelsAdapter {
    TranslateResult toTranslationResult(WordModel dbModel) {
        TranslateDirection direction = new TranslateDirection(LanguageCode.valueOf(dbModel.languageSrc), LanguageCode.valueOf(dbModel.languageDst));
        return new TranslateResult(dbModel.textSrc, dbModel.textDst, direction, true);
    }

    TranslationRowModel toTranslationRowModel(WordModel dbModel) {
        TranslateResult translateResult = toTranslationResult(dbModel);
        return new TranslationRowModel(dbModel.id, translateResult, true);
    }

    TranslationRowModel toTranslationRowModel(WordFavoriteModel dbModel) {
        TranslateDirection direction = new TranslateDirection(LanguageCode.valueOf(dbModel.languageSrc), LanguageCode.valueOf(dbModel.languageDst));
        TranslateResult translateResult = new TranslateResult(dbModel.textSrc, dbModel.textDst, direction, true);
        return new TranslationRowModel(dbModel.id, translateResult, dbModel.favoriteId > 0);
    }

    WordModel toWordModel(TranslateResult translateResult) {
        return new WordModel(translateResult.textSrc, translateResult.textTranslated, translateResult.direction.src.toString(), translateResult.direction.dst.toString());
    }
}
