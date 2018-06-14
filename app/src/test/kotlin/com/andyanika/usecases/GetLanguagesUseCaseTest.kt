package com.andyanika.usecases

import com.andyanika.translator.common.interfaces.LocalRepository
import com.andyanika.translator.common.interfaces.Resources
import com.andyanika.translator.common.interfaces.usecase.GetLanguagesUseCase
import com.andyanika.translator.common.models.LanguageCode
import com.andyanika.translator.common.models.LanguageCode.*
import com.andyanika.translator.common.models.ui.DisplayLanguageModel
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.assertj.core.api.Assertions
import org.junit.After
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
class GetLanguagesUseCaseTest {
    @Mock
    private lateinit var repository: LocalRepository
    @Mock
    private lateinit var resources: Resources

    private lateinit var testScheduler: TestScheduler
    private lateinit var useCase: GetLanguagesUseCaseImpl
    private lateinit var testObserver: TestObserver<List<DisplayLanguageModel>>

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)

        Timber.plant(object : Timber.Tree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                println(message)
            }
        })

        testScheduler = TestScheduler()
        testObserver = TestObserver()
        useCase = GetLanguagesUseCaseImpl(resources, repository, testScheduler)
    }

    @After
    fun after() {
        testObserver.dispose()
    }

    @Test
    fun should_return_single_selected() {
        // given:
        val currentLanguageCode = RU
        Mockito.`when`(repository.srcLanguage).thenReturn(Observable.just(currentLanguageCode))

        val languageCodes = values()
        Mockito.`when`(repository.availableLanguages).thenReturn(Observable.fromArray(*languageCodes))

        useCase.run(true).subscribe(testObserver)

        // when:
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then:
        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        val languages = testObserver.values()[0]

        Assertions.assertThat(languages.size).isEqualTo(languageCodes.size)
        Assertions.assertThat(languages.single { it.isSelected }.code).isEqualTo(currentLanguageCode)
    }

    @Test
    fun should_add_description() {
        val stubDescription = "stub"
        Mockito.`when`(resources.getString(Mockito.anyString())).thenReturn(stubDescription)
        Mockito.`when`(repository.srcLanguage).thenReturn(Observable.just(RU))
        val languageCodes = values()
        Mockito.`when`(repository.availableLanguages)
            .thenReturn(Observable.fromArray(*languageCodes))

        useCase.run(true).subscribe(testObserver)

        // when:
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then:
        val languages = testObserver.values().single()
        Assertions.assertThat(languages.all { it.description == stubDescription }).isTrue()
        Mockito.verify(resources, Mockito.times(languageCodes.size)).getString(anyString())
    }

    @Test
    fun should_call_source_language() {
        // given:
        Mockito.`when`(repository.srcLanguage).thenReturn(Observable.just(RU))
        Mockito.`when`(repository.dstLanguage).thenReturn(Observable.just(EN))
        Mockito.`when`(repository.availableLanguages).thenReturn(Observable.empty())

        useCase.run(true).subscribe(testObserver)

        // when:
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then:
        Mockito.verify(repository, Mockito.times(1)).srcLanguage
        Mockito.verify(repository, Mockito.never()).dstLanguage
    }

    @Test
    fun should_call_destination_language() {
        // given:
        Mockito.`when`(repository.srcLanguage).thenReturn(Observable.just(RU))
        Mockito.`when`(repository.dstLanguage).thenReturn(Observable.just(EN))
        Mockito.`when`(repository.availableLanguages).thenReturn(Observable.empty())

        useCase.run(false).subscribeWith(testObserver)

        // when:
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then:
        Mockito.verify(repository, Mockito.times(1)).dstLanguage
        Mockito.verify(repository, Mockito.never()).srcLanguage
    }

    @Test
    fun should_return_empty_if_no_available_languages() {
        // given:
        Mockito.`when`(repository.srcLanguage).thenReturn(Observable.just(RU))
        Mockito.`when`(repository.dstLanguage).thenReturn(Observable.just(EN))
        Mockito.`when`(repository.availableLanguages).thenReturn(Observable.empty())

        useCase.run(true).subscribeWith(testObserver)
        // when:
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then:
        testObserver.assertNoValues()
        testObserver.assertComplete()
        Mockito.verifyZeroInteractions(resources)
    }
}