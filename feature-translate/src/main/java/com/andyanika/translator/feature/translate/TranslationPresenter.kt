package com.andyanika.translator.feature.translate

import com.andyanika.translator.common.interfaces.usecase.GetSelectedLanguageUseCase
import com.andyanika.translator.common.interfaces.usecase.SelectLanguageUseCase
import com.andyanika.translator.common.interfaces.usecase.TranslationUseCase
import com.andyanika.translator.common.models.ui.DisplayTranslateResult
import com.andyanika.translator.common.scopes.FragmentScope
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

@FragmentScope
class TranslationPresenter @Inject internal constructor(
    private val view: TranslationView,
    private val translateUseCase: TranslationUseCase,
    private val getSelectedLanguagesUseCase: GetSelectedLanguageUseCase,
    private val selectLanguageUseCase: SelectLanguageUseCase,
    private val retrySubject: PublishSubject<String>,
    @param:Named("ui") private val uiScheduler: Scheduler,
    @param:Named("computation") private val computationScheduler: Scheduler
) {
    private var searchDisposable: Disposable? = null
    private var languageDisposable: Disposable? = null
    private var swapDisposable: Disposable? = null
    fun translate(text: String) {
        retrySubject.onNext(text)
    }

    fun clear() {
        view.hideClearBtn()
        view.hideErrorLayout()
        view.clearResult()
    }

    private fun showProgress(charSequence: CharSequence) {
        uiScheduler.scheduleDirect {
            view.hideErrorLayout()
            view.hideOffline()
            view.hideClearBtn()
            if (charSequence.length == 0) {
                view.hideProgress()
                view.clearTranslation()
            } else {
                view.showProgress()
            }
        }
    }

    fun subscribe(searchTextObservable: Observable<CharSequence>?) {
        languageDisposable = getSelectedLanguagesUseCase
            .run()
            .observeOn(uiScheduler)
            .subscribe { (src, dst) ->
                view.setSrcLabel(src)
                view.setDstLabel(dst)
            }

        searchDisposable = searchTextObservable!!
            .map(Function { obj: CharSequence -> obj.toString() })
            .doOnNext { t: String? -> Timber.d("received new text: %s", t) }
            .distinctUntilChanged { obj: String, anObject: String? -> obj.equals(anObject) }
            .mergeWith(retrySubject)
            .doOnNext { charSequence: String -> showProgress(charSequence) }
            .doOnNext { s: String? -> Timber.d("before debounce: %s", s) }
            .debounce(DELAY, TimeUnit.SECONDS, computationScheduler)
            .doOnNext { s: String? -> Timber.d("after debounce: %s", s) }
            .switchMap { srcText ->
                Maybe.fromCallable<DisplayTranslateResult> { coroutineRun(srcText) }.toObservable()
            } //Function<String, ObservableSource<out R>?> { srcText: String? -> coroutineRun })
            .doOnNext { Timber.d("after switch map: %s", it) }
            .observeOn(uiScheduler)
            .subscribe({ processResult(it) }) { throwable: Throwable -> processError(throwable) }
    }

    private fun coroutineRun(srcText: String): DisplayTranslateResult? {
        return runBlocking {
            translateUseCase.run(srcText)
        }
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

    fun swapDirection() {
        swapDisposable = selectLanguageUseCase
            .swap()
            .observeOn(uiScheduler)
            .subscribe { view.clearResult() }
    }

    fun dispose() {
        dispose(searchDisposable)
        dispose(languageDisposable)
        dispose(swapDisposable)
    }

    private fun dispose(disposable: Disposable?) {
        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
        }
    }

    companion object {
        const val DELAY: Long = 1
    }
}
