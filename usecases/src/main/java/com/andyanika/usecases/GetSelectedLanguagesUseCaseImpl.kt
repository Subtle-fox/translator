package com.andyanika.usecases

import com.andyanika.translator.common.interfaces.LocalRepository
import com.andyanika.translator.common.interfaces.Resources
import com.andyanika.translator.common.interfaces.usecase.GetSelectedLanguageUseCase
import com.andyanika.translator.common.models.LanguageCode
import com.andyanika.translator.common.models.TranslateDirection
import io.reactivex.rxjava3.functions.Function
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.*

class GetSelectedLanguagesUseCaseImpl constructor(
    resources: Resources,
    private val repository: LocalRepository,
    private val ioDispatcher: CoroutineDispatcher
) : GetSelectedLanguageUseCase {

    private val toUiStringFunction = Function { code: LanguageCode ->
        resources.getString("lang_" + code.toString().lowercase(Locale.getDefault()))
    }

    override suspend fun run(): TranslateDirection<String> {
        return withContext(ioDispatcher) {
            val srcLanguage = repository.srcLanguage.map(toUiStringFunction)
            val dstLanguage = repository.dstLanguage.map(toUiStringFunction)

            srcLanguage
                .zipWith(dstLanguage, { src, dst -> TranslateDirection(src, dst) })
                .blockingFirst()
        }
    }
}
