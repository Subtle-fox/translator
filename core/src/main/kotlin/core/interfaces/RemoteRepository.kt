package core.interfaces

import core.models.TranslateRequest
import core.models.TranslateResult

interface RemoteRepository {
    suspend fun translate(request: TranslateRequest): TranslateResult?
}
