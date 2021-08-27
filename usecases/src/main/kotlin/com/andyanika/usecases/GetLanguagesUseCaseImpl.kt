package com.andyanika.usecases

import core.interfaces.LocalRepository
import core.interfaces.Resources
import core.interfaces.usecase.GetLanguagesUseCase
import core.models.LanguageCode
import core.models.ui.DisplayLanguageModel
import timber.log.Timber
import java.util.*

internal class GetLanguagesUseCaseImpl(
    private val resources: Resources,
    private val repository: LocalRepository,
) : GetLanguagesUseCase {

    // TODO: move out of the usecase
    private val languageCodeToString = { code: LanguageCode ->
        resources.getString("lang_" + code.toString().lowercase(Locale.getDefault()))
    }

    override suspend fun run(selectSource: Boolean): List<DisplayLanguageModel> {
        val selectedLanguage = if (selectSource) {
            repository.getSrcLanguage()
        } else {
            repository.getDstLanguage()
        }
        Timber.d("onNext: selected lang %s", selectedLanguage)

        return repository
            .getAvailableLanguages()
            .map { code ->
                DisplayLanguageModel(
                    code = code,
                    description = languageCodeToString(code),
                    isSelected = code == selectedLanguage
                )
            }
    }
}
