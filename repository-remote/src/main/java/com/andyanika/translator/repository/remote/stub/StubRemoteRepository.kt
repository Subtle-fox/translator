package com.andyanika.translator.repository.remote.stub

import core.interfaces.RemoteRepository
import core.models.TranslateRequest
import core.models.TranslateResult
import kotlinx.coroutines.delay

internal object StubRemoteRepository : RemoteRepository {
    override suspend fun translate(request: TranslateRequest): TranslateResult {
        println("### 1")
        delay(1000)
        println("### 2")
        return TranslateResult(
            textSrc = request.text,
            textDst = request.text.reversed(),
            direction = request.direction
        ).also {
            println("### 3")
        }
    }
}
