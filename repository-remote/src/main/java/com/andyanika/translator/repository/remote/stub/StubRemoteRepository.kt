package com.andyanika.translator.repository.remote.stub

import core.interfaces.RemoteRepository
import core.models.TranslateRequest
import core.models.TranslateResult
import kotlinx.coroutines.delay

internal object StubRemoteRepository : RemoteRepository {
    override suspend fun translate(request: TranslateRequest): TranslateResult {
        delay(1000)
        return TranslateResult(
            textSrc = request.text,
            textDst = request.text.reversed(),
            direction = request.direction
        )
    }
}
