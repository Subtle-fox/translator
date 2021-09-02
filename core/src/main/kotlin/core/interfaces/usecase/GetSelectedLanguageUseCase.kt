package core.interfaces.usecase

import core.models.LanguageCode
import core.models.TranslateDirection
import kotlinx.coroutines.flow.Flow

interface GetSelectedLanguageUseCase {
    suspend fun run(): Flow<TranslateDirection<LanguageCode>>
}
