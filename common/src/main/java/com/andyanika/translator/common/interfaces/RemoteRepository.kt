package com.andyanika.translator.common.interfaces

import com.andyanika.translator.common.models.TranslateRequest
import com.andyanika.translator.common.models.TranslateResult

interface RemoteRepository {
    suspend fun translate(request: TranslateRequest): TranslateResult?
}
