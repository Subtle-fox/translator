package com.andyanika.datasource.local;

import com.andyanika.datasource.local.model.WordFavoriteModel;
import com.andyanika.datasource.local.model.WordModel;
import com.andyanika.translator.common.models.LanguageCode;
import com.andyanika.translator.common.models.TranslateResult;
import com.andyanika.translator.common.models.TranslationRowModel;

public class ModelsAdapter {
    TranslateResult convert(WordModel dbModel) {
        return new TranslateResult(dbModel.textSrc, dbModel.textDst, LanguageCode.valueOf(dbModel.languageSrc), LanguageCode.valueOf(dbModel.languageDst));
    }

    TranslationRowModel convertToRowModel(WordModel dbModel) {
        TranslateResult translateResult = convert(dbModel);
        return new TranslationRowModel(dbModel.id, translateResult, true);
    }

    TranslationRowModel convertToRowModel(WordFavoriteModel dbModel) {
        TranslateResult translateResult = new TranslateResult(dbModel.textSrc, dbModel.textDst, LanguageCode.RU, LanguageCode.EN);
        return new TranslationRowModel(dbModel.id, translateResult, dbModel.favoriteId > 0);
    }

    WordModel convert(TranslateResult translateResult) {
        return new WordModel(translateResult.textSrc, translateResult.textTranslated, translateResult.languageSrc.toString(), translateResult.languageDst.toString());
    }
}
