package core.interfaces.usecase

import core.models.ui.DisplayLanguageModel

interface GetLanguagesUseCase {
    suspend fun run(selectSource: Boolean): List<DisplayLanguageModel>
}
