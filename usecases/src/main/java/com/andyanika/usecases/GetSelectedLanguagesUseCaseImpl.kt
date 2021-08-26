package com.andyanika.usecases

import com.andyanika.translator.common.interfaces.LocalRepository
import com.andyanika.translator.common.interfaces.Resources
import com.andyanika.translator.common.interfaces.usecase.GetSelectedLanguageUseCase
import com.andyanika.translator.common.models.LanguageCode
import com.andyanika.translator.common.models.TranslateDirection
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.zip
import java.util.*

internal class GetSelectedLanguagesUseCaseImpl constructor(
    private val resources: Resources,
    private val repository: LocalRepository,
) : GetSelectedLanguageUseCase {

    private fun toUiString(code: LanguageCode): String {
        return resources.getString("lang_" + code.toString().lowercase(Locale.getDefault()))
    }

    override suspend fun run(): TranslateDirection<String> {
        val srcLanguage = repository.observeSrcLanguage()
        val dstLanguage = repository.observeDstLanguage()

        return srcLanguage
            .zip(dstLanguage) { src, dst ->
                TranslateDirection(
                    toUiString(src),
                    toUiString(dst)
                )
            }
            .take(1)
            .single()
    }
}
