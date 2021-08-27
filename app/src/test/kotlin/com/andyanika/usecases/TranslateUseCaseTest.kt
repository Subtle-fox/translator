package com.andyanika.usecases

import core.interfaces.LocalRepository
import core.interfaces.RemoteRepository
import core.models.LanguageCode.EN
import core.models.LanguageCode.RU
import core.models.TranslateDirection
import core.models.TranslateRequest
import core.models.TranslateResult
import core.models.ui.DisplayTranslateResult
import io.reactivex.Observable
import io.reactivex.Single
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
class TranslateUseCaseTest {
    @Mock
    private lateinit var remoteRepository: RemoteRepository
    @Mock
    private lateinit var localRepository: LocalRepository

    private lateinit var testScheduler: TestScheduler
    private lateinit var useCase: TranslateUseCaseImpl

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)

        Timber.plant(object : Timber.Tree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                println(message)
            }
        })

        testScheduler = TestScheduler()
        useCase = TranslateUseCaseImpl(localRepository, remoteRepository, testScheduler)
    }

    // ----------- Create Request -----------
    @Test
    fun should_create_translation_request() {
        // given
        val srcText = "src"
        Mockito.`when`(localRepository.srcLanguage).thenReturn(Observable.just(RU))
        Mockito.`when`(localRepository.dstLanguage).thenReturn(Observable.just(EN))

        val testObserver = TestObserver<TranslateRequest>()
        useCase.getTranslationRequest(srcText).subscribe(testObserver)

        // when:
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then:
        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.values().single().run {
            assertThat(this).isEqualTo(TranslateRequest(srcText, TranslateDirection(RU, EN)))
        }
    }

    @Test
    fun should_send_error_if_repository_failed() {
        // given
        val srcText = "src"
        Mockito.`when`(localRepository.srcLanguage).thenReturn(Observable.just(RU))
        val exception = IOException("sd card corrupted")
        Mockito.`when`(localRepository.dstLanguage).thenReturn(Observable.error(exception))

        val testObserver = TestObserver<TranslateRequest>()
        useCase.getTranslationRequest(srcText).subscribe(testObserver)

        // when:
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then:
        testObserver.assertError(exception)
        testObserver.assertValueCount(0)
    }


    // ----------- Local -----------
    @Test
    fun should_get_local_translation() {
        // given
        val request = TranslateRequest("src", TranslateDirection(RU, EN))
        val expectedResult = TranslateResult(request.text, "dst", request.direction)
        Mockito.`when`(localRepository.translate(request)).thenReturn(Single.just(expectedResult))

        val testObserver = TestObserver<DisplayTranslateResult>()
        useCase.translateLocally(request).subscribe(testObserver)

        // when:
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then:
        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.values().single().run {
            assertThat(direction).isEqualTo(request.direction)
            assertThat(textSrc).isEqualTo(request.text)
            assertThat(textTranslated).isEqualTo(expectedResult.textDst)
            assertThat(isError).isFalse()
            assertThat(isFound).isTrue()
            assertThat(isOffline).isTrue()
        }
    }

    @Test
    fun should_return_error_if_not_in_local_db() {
        // given
        val request = TranslateRequest("src", TranslateDirection(RU, EN))
        val error = Exception("no value")
        Mockito.`when`(localRepository.translate(request)).thenReturn(Single.error(error))

        val testObserver = TestObserver<DisplayTranslateResult>()
        useCase.translateLocally(request).subscribe(testObserver)

        // when:
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then:
        testObserver.assertError(error)
    }


    // ----------- Remote -----------
    @Test
    fun should_get_remote_translation() {
        // given
        val request = TranslateRequest("src", TranslateDirection(RU, EN))
        val expectedResult = TranslateResult(request.text, "dst", request.direction)
        Mockito.`when`(localRepository.addTranslation(expectedResult)).thenReturn(Single.just(expectedResult))
        Mockito.`when`(remoteRepository.translate(request)).thenReturn(Observable.just(expectedResult))

        val testObserver = TestObserver<DisplayTranslateResult>()
        useCase.translateRemotely(request).subscribe(testObserver)

        // when:
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then:
        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.values().single().run {
            assertThat(direction).isEqualTo(request.direction)
            assertThat(textSrc).isEqualTo(request.text)
            assertThat(textTranslated).isEqualTo(expectedResult.textDst)
            assertThat(isError).isFalse()
            assertThat(isFound).isTrue()
            assertThat(isOffline).isFalse()
        }
    }

    @Test
    fun should_return_not_found_result_if_not_in_remote_db() {
        // given
        val request = TranslateRequest("src", TranslateDirection(RU, EN))
        Mockito.`when`(remoteRepository.translate(request)).thenReturn(Observable.error(Exception("no value")))

        val testObserver = TestObserver<DisplayTranslateResult>()
        useCase.translateRemotely(request).subscribe(testObserver)

        // when:
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then:
        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.values().single().run {
            assertThat(direction).isEqualTo(request.direction)
            assertThat(textSrc).isEqualTo(request.text)
            assertThat(textTranslated).isNull()
            assertThat(isError).isTrue()
            assertThat(isFound).isFalse()
            assertThat(isOffline).isFalse()
        }
    }

    @Test
    fun should_return_error_result_if_not_in_remote_db() {
        // given
        val request = TranslateRequest("src", TranslateDirection(RU, EN))
        Mockito.`when`(remoteRepository.translate(request)).thenReturn(Observable.empty())

        val testObserver = TestObserver<DisplayTranslateResult>()
        useCase.translateRemotely(request).subscribe(testObserver)

        // when:
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then:
        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.values().single().run {
            assertThat(direction).isEqualTo(request.direction)
            assertThat(textSrc).isEqualTo(request.text)
            assertThat(textTranslated).isNull()
            assertThat(isError).isFalse()
            assertThat(isFound).isFalse()
            assertThat(isOffline).isFalse()
        }
    }

    @Test
    fun should_save_to_local_db() {
        // given
        val request = TranslateRequest("src", TranslateDirection(RU, EN))
        val expectedResultRemote = TranslateResult(request.text, "dst remote", request.direction)
        Mockito.`when`(localRepository.addTranslation(expectedResultRemote)).thenReturn(Single.just(expectedResultRemote))
        Mockito.`when`(remoteRepository.translate(request)).thenReturn(Observable.just(expectedResultRemote))

        val testObserver = TestObserver<DisplayTranslateResult>()
        useCase.translateRemotely(request).subscribe(testObserver)

        // when:
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then:
        testObserver.assertComplete()
        Mockito.verify(localRepository, Mockito.times(1)).addTranslation(expectedResultRemote)

        testObserver.assertValueCount(1)
        testObserver.values().single().run {
            assertThat(direction).isEqualTo(request.direction)
            assertThat(textSrc).isEqualTo(request.text)
            assertThat(textTranslated).isEqualTo(expectedResultRemote.textDst)
            assertThat(isError).isFalse()
            assertThat(isFound).isTrue()
            assertThat(isOffline).isFalse()
        }
    }

    @Test
    fun should_not_fail_if_saving_throws() {
        // given
        val request = TranslateRequest("src", TranslateDirection(RU, EN))
        val expectedResultRemote = TranslateResult(request.text, "dst remote", request.direction)
        Mockito.`when`(localRepository.addTranslation(expectedResultRemote)).thenReturn(Single.error( IOException() ))
        Mockito.`when`(remoteRepository.translate(request)).thenReturn(Observable.just(expectedResultRemote))

        val testObserver = TestObserver<DisplayTranslateResult>()
        useCase.translateRemotely(request).subscribe(testObserver)

        // when:
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then:
        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.values().single().run {
            assertThat(direction).isEqualTo(request.direction)
            assertThat(textSrc).isEqualTo(request.text)
            assertThat(textTranslated).isEqualTo(expectedResultRemote.textDst)
            assertThat(isError).isFalse()
            assertThat(isFound).isTrue()
            assertThat(isOffline).isFalse()
        }
    }

    // ----------- Composition -----------

    @Test
    fun should_return_empty_if_empty_string() {
        // given
        val testObserver = TestObserver<DisplayTranslateResult>()
        useCase.run("").subscribe(testObserver)

        // when:
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then:
        testObserver.assertComplete()
        testObserver.assertValueCount(0)
    }

    @Test
    fun should_fetch_from_local_first() {
        // given
        Mockito.`when`(localRepository.srcLanguage).thenReturn(Observable.just(RU))
        Mockito.`when`(localRepository.dstLanguage).thenReturn(Observable.just(EN))

        val request = TranslateRequest("src", TranslateDirection(RU, EN))
        val expectedResultLocal = TranslateResult(request.text, "dst local", request.direction)
        Mockito.`when`(localRepository.translate(request)).thenReturn(Single.just(expectedResultLocal))
        Mockito.`when`(remoteRepository.translate(Mockito.any())).thenReturn(Observable.error(IllegalStateException("should not be called")))

        val testObserver = TestObserver<DisplayTranslateResult>()
        useCase.run(request.text).subscribe(testObserver)

        // when:
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then:
        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.values().single().run {
            assertThat(direction).isEqualTo(request.direction)
            assertThat(textSrc).isEqualTo(request.text)
            assertThat(textTranslated).isEqualTo(expectedResultLocal.textDst)
            assertThat(isError).isFalse()
            assertThat(isFound).isTrue()
            assertThat(isOffline).isTrue()
        }
    }

    @Test
    fun should_fetch_from_remote_if_absent_in_local() {
        // given
        Mockito.`when`(localRepository.srcLanguage).thenReturn(Observable.just(RU))
        Mockito.`when`(localRepository.dstLanguage).thenReturn(Observable.just(EN))

        val request = TranslateRequest("src", TranslateDirection(RU, EN))
        val expectedResultRemote = TranslateResult(request.text, "dst remote", request.direction)
        Mockito.`when`(localRepository.translate(request)).thenReturn(Single.error(Exception("not saved")))
        Mockito.`when`(localRepository.addTranslation(expectedResultRemote)).thenReturn(Single.just(expectedResultRemote))
        Mockito.`when`(remoteRepository.translate(request)).thenReturn(Observable.just(expectedResultRemote))

        val testObserver = TestObserver<DisplayTranslateResult>()
        useCase.run(request.text).subscribe(testObserver)

        // when:
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then:
        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.values().single().run {
            assertThat(direction).isEqualTo(request.direction)
            assertThat(textSrc).isEqualTo(request.text)
            assertThat(textTranslated).isEqualTo(expectedResultRemote.textDst)
            assertThat(isError).isFalse()
            assertThat(isFound).isTrue()
            assertThat(isOffline).isFalse()
        }
    }

    @Test
    fun should_return_error_result_if_repository_fails() {
        // given
        Mockito.`when`(localRepository.srcLanguage).thenReturn(Observable.just(RU))
        Mockito.`when`(localRepository.dstLanguage).thenReturn(Observable.just(EN))

        val request = TranslateRequest("src", TranslateDirection(RU, EN))
        Mockito.`when`(localRepository.translate(request)).thenReturn(Single.error(Exception("not saved")))
        Mockito.`when`(remoteRepository.translate(request)).thenReturn(Observable.error(IOException("server is down")))

        val testObserver = TestObserver<DisplayTranslateResult>()
        useCase.run(request.text).subscribe(testObserver)

        // when:
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then:
        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.values().single().run {
            assertThat(direction).isEqualTo(request.direction)
            assertThat(textSrc).isEqualTo(request.text)
            assertThat(textTranslated).isNull()
            assertThat(isError).isTrue()
            assertThat(isFound).isFalse()
            assertThat(isOffline).isFalse()
        }
    }
}
