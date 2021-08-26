package com.andyanika.usecases

import com.andyanika.translator.common.interfaces.LocalRepository
import com.andyanika.translator.common.interfaces.Resources
import com.andyanika.translator.common.interfaces.usecase.GetLanguagesUseCase
import com.andyanika.translator.common.models.LanguageCode
import com.andyanika.translator.common.models.LanguageDescription
import com.andyanika.translator.common.models.ui.DisplayLanguageModel
import timber.log.Timber
import java.util.*
import javax.inject.Inject

internal class GetLanguagesUseCaseImpl @Inject constructor(
    private val resources: Resources,
    private val repository: LocalRepository,
) : GetLanguagesUseCase {

    override suspend fun run(selectSource: Boolean): List<DisplayLanguageModel> {
        val selectedLanguage = if (selectSource) {
            repository.getSrcLanguage()
        } else {
            repository.getDstLanguage()
        }
        Timber.d("onNext: selected lang %s", selectedLanguage)

        return repository
            .getAvailableLanguages()
            .map { code: LanguageCode ->
                LanguageDescription(code, resources.getString("lang_" + code.toString().lowercase(Locale.getDefault())))
            }
            .map { desc: LanguageDescription ->
                DisplayLanguageModel(
                    code = desc.code,
                    description = desc.description,
                    isSelected = desc.code == selectedLanguage
                )
            }
    }
}
