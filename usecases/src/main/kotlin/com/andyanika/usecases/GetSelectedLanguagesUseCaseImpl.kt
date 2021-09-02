package com.andyanika.usecases

import core.interfaces.LocalRepository
import core.interfaces.Resources
import core.interfaces.usecase.GetSelectedLanguageUseCase
import core.models.LanguageCode
import core.models.TranslateDirection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

internal class GetSelectedLanguagesUseCaseImpl constructor(
    private val resources: Resources,
    private val repository: LocalRepository,
) : GetSelectedLanguageUseCase {

    override suspend fun run(): Flow<TranslateDirection<LanguageCode>> {
        return combine(
            repository.observeSrcLanguage(),
            repository.observeDstLanguage()
        ) { src, dst -> TranslateDirection(src, dst) }
    }
}
