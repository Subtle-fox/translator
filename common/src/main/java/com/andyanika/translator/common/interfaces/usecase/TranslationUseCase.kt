package com.andyanika.translator.common.interfaces.usecase

import com.andyanika.translator.common.models.ui.DisplayTranslateResult

interface TranslationUseCase {
    suspend fun run(srcText: String): DisplayTranslateResult?
}
