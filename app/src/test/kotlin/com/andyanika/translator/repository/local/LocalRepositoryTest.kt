package com.andyanika.translator.repository.local

import android.content.SharedPreferences
import com.andyanika.translator.common.models.LanguageCode
import com.andyanika.translator.common.models.LanguageCode.EN
import com.andyanika.translator.common.models.LanguageCode.RU
import com.andyanika.translator.common.models.TranslateDirection
import com.andyanika.translator.repository.local.LocalRepositoryImpl.LANGUAGE_DST
import com.andyanika.translator.repository.local.LocalRepositoryImpl.LANGUAGE_SRC
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
import java.io.IOException
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class LocalRepositoryTest {
    @Mock
    private lateinit var prefs: SharedPreferences

    @Mock
    private lateinit var dao: TranslatorDao

    @Mock
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var testScheduler: TestScheduler
    private lateinit var repository: LocalRepositoryImpl

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        testScheduler = TestScheduler()
        repository = LocalRepositoryImpl(dao, prefs, ModelsAdapter(), testScheduler)
    }

    @Test
    fun should_return_result() {
        // given
        Mockito.`when`(prefs.getString(LANGUAGE_SRC, null)).thenReturn("ru")
        val testObserver = TestObserver<LanguageCode>()
        repository.srcLanguage.subscribe(testObserver)

        // when
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then
        testObserver.assertValueCount(1)
        testObserver.values()[0].run {
            assertThat(this).isEqualTo(RU)
        }
    }

    @Test
    fun should_return_ru_as_src_default_if_not_saved() {
        // given
        Mockito.`when`(prefs.getString(LANGUAGE_SRC, null)).thenReturn(null)
        val testObserver = TestObserver<LanguageCode>()
        repository.srcLanguage.subscribe(testObserver)

        // when
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then
        testObserver.assertValueCount(1)
        testObserver.values()[0].run {
            assertThat(this).isEqualTo(RU)
        }
    }

    @Test
    fun should_return_ru_as_src_default_if_exception() {
        // given
        Mockito.`when`(prefs.getString(LANGUAGE_SRC, null)).then { throw IOException() }
        val testObserver = TestObserver<LanguageCode>()
        repository.srcLanguage.subscribe(testObserver)

        // when
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then
        testObserver.assertValueCount(1)
        testObserver.values()[0].run {
            assertThat(this).isEqualTo(RU)
        }
    }

    @Test
    fun should_return_en_as_dst_default_if_not_saved() {
        // given
        Mockito.`when`(prefs.getString(LANGUAGE_DST, null)).thenReturn(null)
        val testObserver = TestObserver<LanguageCode>()
        repository.dstLanguage.subscribe(testObserver)

        // when
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then
        testObserver.assertValueCount(1)
        testObserver.values()[0].run {
            assertThat(this).isEqualTo(EN)
        }
    }


    @Test
    fun should_report_changes_after_repository_changed() {
        // given
        Mockito.`when`(prefs.getString(LANGUAGE_SRC, null)).thenReturn(null)
        Mockito.`when`(prefs.edit()).thenReturn(editor)
        Mockito.`when`(editor.putString(Mockito.anyString(), Mockito.anyString())).thenReturn(editor)

        val testObserver = TestObserver<LanguageCode>()
        repository.srcLanguage.subscribe(testObserver)
        testScheduler.scheduleDirect({ repository.setLanguageDirection(TranslateDirection(EN, RU)) }, 2, TimeUnit.SECONDS)

        // when
        testScheduler.advanceTimeTo(1, TimeUnit.SECONDS)

        // then
        testObserver.assertValueCount(1)
        testObserver.values()[0].run {
            assertThat(this).isEqualTo(RU)
        }
        // when
        testScheduler.advanceTimeTo(3, TimeUnit.SECONDS)

        // then
        testObserver.assertValueCount(2)
        testObserver.values()[1].run {
            assertThat(this).isEqualTo(EN)
        }
    }
}