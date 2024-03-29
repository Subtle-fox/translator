package com.andyanika.translator.feature.translate

import core.interfaces.usecase.GetSelectedLanguageUseCase
import core.interfaces.usecase.SelectLanguageUseCase
import core.interfaces.usecase.TranslationUseCase
import core.models.ui.DisplayTranslateResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.concurrent.TimeUnit


// TODO: migrate to ViewModel
internal class TranslationPresenter constructor(
    private val view: TranslationView,
    private val translateUseCase: TranslationUseCase,
    private val getSelectedLanguagesUseCase: GetSelectedLanguageUseCase,
    private val selectLanguageUseCase: SelectLanguageUseCase,
) {
    private val retryFlow = MutableSharedFlow<String>(0)

    suspend fun translate(text: String) {
        retryFlow.emit(text)
    }

    fun clear() {
        view.hideClearBtn()
        view.hideErrorLayout()
        view.clearResult()
    }

    private fun showProgress(charSequence: CharSequence) {
        view.hideErrorLayout()
        view.hideOffline()
        view.hideClearBtn()
        if (charSequence.isEmpty()) {
            view.hideProgress()
            view.clearTranslation()
        } else {
            view.showProgress()
        }
    }

    suspend fun subscribeLanguageDirection() {
        getSelectedLanguagesUseCase
            .run()
            .collect { (src, dst) ->
                view.setSrcLabel(src)
                view.setDstLabel(dst)
            }
    }

    suspend fun subscribe(inputTextFlow: Flow<String>) {
        merge(inputTextFlow.distinctUntilChanged(), retryFlow)
            .flowOn(Dispatchers.Default)
            .onEach(::showProgress)
            .debounce(TimeUnit.SECONDS.toMillis(DELAY))
            .mapNotNull(translateUseCase::run)
            .onEach(::processResult)
            .catch { throwable -> processError(throwable) }
            .collect()
    }

    private fun processError(throwable: Throwable) {
        throwable.printStackTrace()
        view.showErrorLayout()
        view.clearTranslation()
        view.hideProgress()
        view.showClearBtn()
        view.hideOffline()
    }

    private fun processResult(result: DisplayTranslateResult) {
        Timber.d("process result: %s", result.textTranslated)
        if (result.isFound) {
            processFoundResult(result)
        } else {
            processEmptyResult(result)
        }
    }

    private fun processFoundResult(result: DisplayTranslateResult) {
        view.showTranslation(result)
        view.hideProgress()
        view.showClearBtn()
        if (result.isOffline) {
            view.showOffline()
        } else {
            view.hideOffline()
        }
    }

    private fun processEmptyResult(result: DisplayTranslateResult) {
        if (result.isError) {
            view.showErrorLayout()
            view.clearTranslation()
        } else {
            view.showNotFound()
        }
        view.hideProgress()
        view.showClearBtn()
        view.hideOffline()
    }

    suspend fun swapDirection() {
        selectLanguageUseCase.swap()
        view.clearResult()
    }

    companion object {
        const val DELAY: Long = 1
    }
}
