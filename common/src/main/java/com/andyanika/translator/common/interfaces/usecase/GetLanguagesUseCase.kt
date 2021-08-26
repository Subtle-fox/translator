package com.andyanika.translator.common.interfaces.usecase

import com.andyanika.translator.common.models.ui.DisplayLanguageModel

interface GetLanguagesUseCase {
    suspend fun run(selectSource: Boolean): List<DisplayLanguageModel>
}
