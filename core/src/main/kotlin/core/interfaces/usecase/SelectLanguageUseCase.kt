package core.interfaces.usecase

import core.models.LanguageCode

interface SelectLanguageUseCase {
    suspend fun setSrc(code: LanguageCode)
    suspend fun setDst(code: LanguageCode)
    suspend fun swap()
}
