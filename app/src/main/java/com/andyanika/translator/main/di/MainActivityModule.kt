package com.andyanika.translator.main.di

import android.content.Context
import com.andyanika.translator.common.scopes.ActivityScope
import com.andyanika.translator.main.MainActivity
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module
internal abstract class MainActivityModule {
    @ActivityScope
    @Named("activity")
    @Binds
    abstract fun getActivityContext(activity: MainActivity?): Context?
}
