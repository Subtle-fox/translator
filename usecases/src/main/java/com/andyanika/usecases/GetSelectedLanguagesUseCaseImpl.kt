package com.andyanika.usecases

import com.andyanika.translator.common.interfaces.LocalRepository
import com.andyanika.translator.common.interfaces.Resources
import com.andyanika.translator.common.interfaces.usecase.GetSelectedLanguageUseCase
import com.andyanika.translator.common.models.LanguageCode
import com.andyanika.translator.common.models.TranslateDirection
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.functions.Function
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class GetSelectedLanguagesUseCaseImpl @Inject constructor(
    resources: Resources,
    private val repository: LocalRepository,
    @Named("io") ioScheduler: Scheduler
) : GetSelectedLanguageUseCase {
    private val toUiStringFunction: Function<LanguageCode, String>
    private val ioScheduler: Scheduler
    override fun run(): Observable<TranslateDirection<String>> {
        val srcLanguage = repository.srcLanguage.map(toUiStringFunction)
        val dstLanguage = repository.dstLanguage.map(toUiStringFunction)
        return srcLanguage
            .subscribeOn(ioScheduler)
            .zipWith(dstLanguage, { src: String?, dst: String? ->
                TranslateDirection(
                    src!!, dst!!
                )
            })
    }

    init {
        toUiStringFunction =
            Function { code: LanguageCode ->
                resources.getString(
                    "lang_" + code.toString().lowercase(Locale.getDefault())
                )
            }
        this.ioScheduler = ioScheduler
    }
}
