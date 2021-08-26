package com.andyanika.translator.common.interfaces.usecase

import com.andyanika.translator.common.models.LanguageCode

interface SelectLanguageUseCase {
    suspend fun setSrc(code: LanguageCode)
    suspend fun setDst(code: LanguageCode)
    suspend fun swap()
}
