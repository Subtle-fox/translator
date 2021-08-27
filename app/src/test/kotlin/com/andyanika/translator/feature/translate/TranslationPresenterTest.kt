package com.andyanika.translator.feature.translate

import core.interfaces.usecase.GetSelectedLanguageUseCase
import core.interfaces.usecase.SelectLanguageUseCase
import core.interfaces.usecase.TranslationUseCase
import core.models.LanguageCode.EN
import core.models.LanguageCode.RU
import core.models.TranslateDirection
import core.models.ui.DisplayTranslateResult
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.PublishSubject
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Matchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.runners.MockitoJUnitRunner
import timber.log.Timber
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class TranslationPresenterTest {
    @Mock
    private lateinit var translationUseCase: TranslationUseCase
    @Mock
    private lateinit var getSelectedLanguageUseCase: GetSelectedLanguageUseCase
    @Mock
    private lateinit var selectLanguageUseCase: SelectLanguageUseCase
    @Mock
    private lateinit var view: TranslationView

    private lateinit var retrySubject: PublishSubject<String>
    private lateinit var textSubject: PublishSubject<CharSequence>
    private lateinit var testScheduler: TestScheduler
    private lateinit var presenter: TranslationPresenter

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        testScheduler = TestScheduler()
        retrySubject = PublishSubject.create()
        textSubject = PublishSubject.create()
        presenter = TranslationPresenter(
            view, translationUseCase, getSelectedLanguageUseCase, selectLanguageUseCase,
            retrySubject, testScheduler, testScheduler
        )

        Timber.plant(object : Timber.Tree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                println(message)
            }
        })
    }

    @Test
    fun delay_should_be_1_sec() {
        assertThat(TranslationPresenter.DELAY).isEqualTo(1)
    }

    @Test
    fun should_start_search_progress_when_text_changes() {
        // given
        Mockito.`when`(getSelectedLanguageUseCase.run()).thenReturn(Observable.empty())

        val result = DisplayTranslateResult("src", "dst", TranslateDirection(RU, EN), true)
        Mockito.`when`(translationUseCase.run(result.textSrc)).thenReturn(Observable.just(result))
        presenter.subscribe(textSubject)

        // when:
        textSubject.onNext("src")
        testScheduler.advanceTimeTo(1, TimeUnit.MILLISECONDS)

        // then:
        Mockito.verify(view, Mockito.times(1)).showProgress();
        Mockito.verify(view, Mockito.times(0)).hideProgress();
        Mockito.verify(view, Mockito.times(1)).hideOffline();
        Mockito.verify(view, Mockito.times(1)).hideClearBtn();
        Mockito.verify(view, Mockito.times(0)).clearTranslation();
    }

    @Test
    fun should_clear_search_when_text_changes_to_empty() {
        // given
        Mockito.`when`(getSelectedLanguageUseCase.run()).thenReturn(Observable.empty())

        val result = DisplayTranslateResult("src", "dst", TranslateDirection(RU, EN), true)
        Mockito.`when`(translationUseCase.run(result.textSrc)).thenReturn(Observable.just(result))
        presenter.subscribe(textSubject)

        // when:
        textSubject.onNext("")
        testScheduler.advanceTimeTo(1, TimeUnit.MILLISECONDS)

        // then:
        Mockito.verify(view, Mockito.times(1)).hideProgress();
        Mockito.verify(view, Mockito.times(0)).showProgress();
        Mockito.verify(view, Mockito.times(1)).hideOffline();
        Mockito.verify(view, Mockito.times(1)).hideClearBtn();
        Mockito.verify(view, Mockito.times(1)).clearTranslation();
    }

    @Test
    fun should_consider_debounce() {
        // given
        Mockito.`when`(getSelectedLanguageUseCase.run()).thenReturn(Observable.empty())
        val result = DisplayTranslateResult("src", "dst", TranslateDirection(RU, EN), true)
        Mockito.`when`(translationUseCase.run(anyString())).thenReturn(Observable.just(result))
        presenter.subscribe(textSubject)

        // when:
        textSubject.onNext("src 1")
        testScheduler.scheduleDirect({ textSubject.onNext("src 2") }, 500, TimeUnit.MILLISECONDS)
        testScheduler.advanceTimeTo(1501, TimeUnit.MILLISECONDS)

        // then:
        Mockito.verify(translationUseCase, Mockito.times(0)).run("src 1");
        Mockito.verify(translationUseCase, Mockito.times(1)).run("src 2");
    }

    @Test
    fun should_discard_stale_result() {
        // given
        Mockito.`when`(getSelectedLanguageUseCase.run()).thenReturn(Observable.empty())
        val txt1 = "src 1"
        val result1 = DisplayTranslateResult(txt1, "dst 1", TranslateDirection(RU, EN), true)
        Mockito.`when`(translationUseCase.run(txt1))
            .thenReturn(
                Observable.just(result1)
                    .doOnNext { Timber.d("translation 1 before delay: %s", it) }
                    .delay(1100, TimeUnit.MILLISECONDS, testScheduler)
                    .doOnNext { Timber.d("translation 1 after delay: %s", it) }
            )

        val txt2 = "src 2"
        val result2 = DisplayTranslateResult(txt2, "dst 2", TranslateDirection(RU, EN), true)
        Mockito.`when`(translationUseCase.run(txt2))
            .thenReturn(
                Observable.just(result2)
                    .doOnNext { Timber.d("translation 2 before delay: %s", it) }
                    .delay(500, TimeUnit.MILLISECONDS, testScheduler)
                    .doOnNext { Timber.d("translation 2 after delay: %s", it) }
            )
        presenter.subscribe(textSubject)

        // when:
        textSubject.onNext(txt1)
        testScheduler.scheduleDirect({ textSubject.onNext(txt2) }, 1001, TimeUnit.MILLISECONDS)
        testScheduler.advanceTimeTo(3, TimeUnit.SECONDS)

        // then:
        Mockito.verify(translationUseCase, Mockito.times(1)).run(txt1);
        Mockito.verify(translationUseCase, Mockito.times(1)).run(txt2);

        // but:
        Mockito.verify(view, Mockito.times(0)).showTranslation(result1);
        Mockito.verify(view, Mockito.times(1)).showTranslation(result2);
    }
}
