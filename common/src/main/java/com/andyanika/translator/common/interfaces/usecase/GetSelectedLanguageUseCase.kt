package com.andyanika.translator.common.interfaces.usecase

import com.andyanika.translator.common.models.TranslateDirection

interface GetSelectedLanguageUseCase {
    suspend fun run(): TranslateDirection<String>
}
