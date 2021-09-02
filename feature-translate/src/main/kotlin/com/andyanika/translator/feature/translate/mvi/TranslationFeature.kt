package com.andyanika.translator.feature.translate.mvi

import core.interfaces.usecase.GetSelectedLanguageUseCase
import core.interfaces.usecase.SelectLanguageUseCase
import core.interfaces.usecase.TranslationUseCase
import core.models.ui.DisplayTranslateResult
import kotlinx.coroutines.flow.*
import timber.log.Timber

class TranslationFeature(
    private val translateUseCase: TranslationUseCase,
    private val getSelectedLanguagesUseCase: GetSelectedLanguageUseCase,
    private val selectLanguageUseCase: SelectLanguageUseCase,
) {

    companion object {
        const val DELAY_MS = 300L
    }

    private var state = TranslationViewState.INITIAL
    private val retryFlow = MutableSharedFlow<Unit>()
    private val inputTextFlow = MutableSharedFlow<String>(1)

    suspend fun accept(action: TranslationAction) {
        when (action) {
            is TranslationAction.Translate -> inputTextFlow.tryEmit(action.text)
            is TranslationAction.Retry -> retryFlow.tryEmit(Unit)
            is TranslationAction.Clear -> inputTextFlow.tryEmit("")
            is TranslationAction.SwapDirection -> selectLanguageUseCase.swap()
        }
    }

    suspend fun observe(): Flow<TranslationViewState> {
        return combine(
            inputTextFlow.onEach { state = state.copy(input = it) },
            retryFlow.onStart { emit(Unit) },
            getSelectedLanguagesUseCase.run().onEach { state = state.copy(srcCode = it.src, dstCode = it.dst) }
        ) { txtString, _, direction -> txtString }
            .debounce(DELAY_MS)
            .flatMapLatest { txtString ->
                flow {
                    emit(getProgressState(txtString))

                    translateUseCase
                        .run(txtString)
                        ?.let(::processResult)
                        ?.let { emit(it) }
                }
            }
            .onStart {
                val direction = getSelectedLanguagesUseCase.run().take(1).single()
                state = state.copy(srcCode = direction.src, dstCode = direction.dst)
                emit(state)
            }
            .catch { throwable -> getErrorState(throwable) }
            .filterNotNull()
    }

    private fun processResult(result: DisplayTranslateResult): TranslationViewState {
        Timber.d("process result: %s", result.textTranslated)
        state = state.let {
            if (result.isFound && result.textTranslated?.isNotEmpty() == true) {
                getFoundState(it, result)
            } else {
                getEmptyState(it, result)
            }
        }
        return state
    }

    private fun getFoundState(
        previousState: TranslationViewState,
        result: DisplayTranslateResult
    ): TranslationViewState {
        return previousState.copy(
            output = result.textTranslated.orEmpty(),
            isLoading = false,
            canClear = true,
            isOffline = result.isOffline,
        )
    }

    private fun getEmptyState(
        previousState: TranslationViewState,
        result: DisplayTranslateResult
    ): TranslationViewState {
        return previousState.copy(
            output = "",
            input = "",
            isLoading = false,
            canClear = false,
            isOffline = false,
            isNotFound = !result.isError
        )
    }

    private fun getErrorState(throwable: Throwable): TranslationViewState {
        throwable.printStackTrace()

        state = TranslationViewState.INITIAL.copy(
            isError = true,
            canClear = true
        )

        return state
    }

    private fun getProgressState(srcTxt: String): TranslationViewState {
        state = if (srcTxt.isNotEmpty()) {
            state.copy(
                isNotFound = false,
                isError = false,
                isLoading = true,
                canClear = false,
            )
        } else {
            TranslationViewState.INITIAL
        }

        return state
    }
}
