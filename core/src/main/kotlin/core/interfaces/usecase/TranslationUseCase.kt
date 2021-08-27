package core.interfaces.usecase

import core.models.ui.DisplayTranslateResult

interface TranslationUseCase {
    suspend fun run(srcText: String): DisplayTranslateResult?
}
