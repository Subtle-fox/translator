package com.andyanika.usecases

import com.andyanika.translator.common.interfaces.LocalRepository
import com.andyanika.translator.common.interfaces.Resources
import com.andyanika.translator.common.interfaces.usecase.GetLanguagesUseCase
import com.andyanika.translator.common.models.LanguageCode
import com.andyanika.translator.common.models.LanguageDescription
import com.andyanika.translator.common.models.ui.DisplayLanguageModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class GetLanguagesUseCaseImpl @Inject constructor(
    private val resources: Resources, private val repository: LocalRepository,
    @param:Named("io") private val ioScheduler: Scheduler
) : GetLanguagesUseCase {
    override fun run(selectSource: Boolean): Observable<List<DisplayLanguageModel>> {
        val selectedLanguage = (if (selectSource) repository.srcLanguage else repository.dstLanguage)
            .doOnNext { l: LanguageCode? -> Timber.d("onNext: selected lang %s", l) }
        val availableLanguages = repository
            .availableLanguages.doOnNext { l: LanguageCode? -> Timber.d("onNext: available lang %s", l) }
            .map { code: LanguageCode ->
                LanguageDescription(
                    code,
                    resources.getString("lang_" + code.toString().toLowerCase())
                )
            }
        val combineLatest = selectedLanguage
            .take(1)
            .flatMap { code: LanguageCode ->
                availableLanguages
                    .map { desc: LanguageDescription ->
                        DisplayLanguageModel(
                            desc.code,
                            desc.description,
                            desc.code == code
                        )
                    }
                    .doOnNext { desc: DisplayLanguageModel -> Timber.d("onMapped: lang %s", desc.description) }
            }
        return combineLatest
            .subscribeOn(ioScheduler)
            .buffer(100)
            .doOnNext { list: List<DisplayLanguageModel> -> Timber.d("onNext: list of %d elements", list.size) }
    }
}
