package com.andyanika.translator.common.interfaces.usecase

import com.andyanika.translator.common.models.ui.DisplayTranslateResult
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.annotations.Nullable

interface TranslationUseCase {
    suspend fun run(srcText: @NonNull String?): @Nullable DisplayTranslateResult?
}
