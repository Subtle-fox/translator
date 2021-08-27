package com.andyanika.usecases

import core.interfaces.LocalRepository
import core.interfaces.Resources
import core.interfaces.usecase.GetSelectedLanguageUseCase
import core.models.LanguageCode
import core.models.TranslateDirection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.*

internal class GetSelectedLanguagesUseCaseImpl constructor(
    private val resources: Resources,
    private val repository: LocalRepository,
) : GetSelectedLanguageUseCase {

    // TODO: move out of the usecase
    private val languageCodeToString = { code: LanguageCode ->
        resources.getString("lang_" + code.toString().lowercase(Locale.getDefault()))
    }

    override suspend fun run(): Flow<TranslateDirection<String>> {
        return combine(
            repository.observeSrcLanguage(),
            repository.observeDstLanguage()
        ) { src, dst ->
            TranslateDirection(
                src.let(languageCodeToString),
                dst.let(languageCodeToString)
            )
        }
    }
}
