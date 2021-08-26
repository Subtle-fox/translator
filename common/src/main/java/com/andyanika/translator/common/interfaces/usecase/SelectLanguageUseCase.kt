package com.andyanika.translator.common.interfaces.usecase

import com.andyanika.translator.common.models.LanguageCode
import io.reactivex.rxjava3.core.Completable

interface SelectLanguageUseCase {
    fun setSrc(code: LanguageCode): Completable
    fun setDst(code: LanguageCode): Completable
    suspend fun swap()
}
