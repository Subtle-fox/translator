package com.andyanika.translator.repository.remote

import com.andyanika.translator.common.models.LanguageCode.EN
import com.andyanika.translator.common.models.LanguageCode.RU
import com.andyanika.translator.common.models.TranslateDirection
import com.andyanika.translator.common.models.TranslateRequest
import com.andyanika.translator.common.models.TranslateResult
import com.andyanika.translator.repository.remote.yandex.YandexRemoteRepository
import com.andyanika.translator.repository.remote.yandex.YandexApi
import com.andyanika.translator.repository.remote.yandex.YandexModelsAdapter
import com.andyanika.translator.repository.remote.yandex.YandexTranslationParamsBuilder
import com.andyanika.translator.repository.remote.yandex.YandexTranslationResponse
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.runners.MockitoJUnitRunner
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class RemoteRepositoryTest {
    @Mock
    private lateinit var api: YandexApi

    private val paramsBuilder = YandexTranslationParamsBuilder()
    private val modelsAdapter = YandexModelsAdapter()
    private lateinit var testScheduler: TestScheduler
    private lateinit var repository: YandexRemoteRepository

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        testScheduler = TestScheduler()
        repository = YandexRemoteRepository(api, paramsBuilder, modelsAdapter)

        Timber.plant(object : Timber.Tree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                println(message)
            }
        })
    }

    @Test
    fun should_return_translation_result() {
        // given
        val request = TranslateRequest("src", TranslateDirection(RU, EN))
        val langParams = paramsBuilder.buildParam(request.direction)
        val translatedTxt = "translated"
        val expectedResponse = YandexTranslationResponse().apply {
            code = 200
            languageDirection = langParams
            translatedText = arrayListOf(translatedTxt)
        }

        Mockito.`when`(api.translate(BuildConfig.ApiKey, request.text, langParams))
            .thenReturn(Observable.just(expectedResponse))

        val testObserver = TestObserver<TranslateResult>()
        repository.translate(request)
            .subscribeOn(testScheduler)
            .subscribe(testObserver)

        // when
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then
        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.values().single().run {
            assertThat(textSrc).isEqualTo(request.text)
            assertThat(textDst).isEqualTo(translatedTxt)
        }
    }

    @Test
    fun should_return_empty_if_yandex_api_returns_same_answer() {
        // given
        val srcTxt = "src"
        val request = TranslateRequest(srcTxt, TranslateDirection(RU, EN))
        val langParams = paramsBuilder.buildParam(request.direction)
        val translatedTxt = srcTxt
        val expectedResponse = YandexTranslationResponse().apply {
            code = 200
            languageDirection = langParams
            translatedText = arrayListOf(translatedTxt)
        }

        Mockito.`when`(api.translate(BuildConfig.ApiKey, request.text, langParams))
            .thenReturn(Observable.just(expectedResponse))

        val testObserver = TestObserver<TranslateResult>()
        repository.translate(request)
            .subscribeOn(testScheduler)
            .subscribe(testObserver)

        // when
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then
        testObserver.assertComplete()
        testObserver.assertNoValues()
    }

    @Test
    fun should_return_empty_if_yandex_api_returns_empty_array() {
        // given
        val request = TranslateRequest("src", TranslateDirection(RU, EN))
        val langParams = paramsBuilder.buildParam(request.direction)
        val expectedResponse = YandexTranslationResponse().apply {
            code = 200
            languageDirection = langParams
            translatedText = arrayListOf()
        }

        Mockito.`when`(api.translate(BuildConfig.ApiKey, request.text, langParams))
            .thenReturn(Observable.just(expectedResponse))

        val testObserver = TestObserver<TranslateResult>()
        repository.translate(request)
            .subscribeOn(testScheduler)
            .subscribe(testObserver)

        // when
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then
        testObserver.assertComplete()
        testObserver.assertNoValues()
    }

    @Test
    fun should_return_error_if_yandex_api_returns_error() {
        // given
        val request = TranslateRequest("src", TranslateDirection(RU, EN))
        val langParams = paramsBuilder.buildParam(request.direction)
        val ioException = IOException()
        Mockito.`when`(api.translate(BuildConfig.ApiKey, request.text, langParams))
            .thenReturn(Observable.error(ioException))

        val testObserver = TestObserver<TranslateResult>()
        repository.translate(request)
            .subscribeOn(testScheduler)
            .subscribe(testObserver)

        // when
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then
        testObserver.assertError(ioException)
    }
}
