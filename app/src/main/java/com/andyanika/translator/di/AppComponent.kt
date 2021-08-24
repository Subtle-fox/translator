package com.andyanika.translator.di

import com.andyanika.translator.App
import com.andyanika.translator.common.scopes.ApplicationScope
import com.andyanika.translator.main.di.MainBindingModule
import com.andyanika.translator.repository.local.LocalRepositoryModule
import com.andyanika.translator.repository.remote.RemoteRepositoryComponent
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@ApplicationScope
@Component(
    modules = [AndroidInjectionModule::class, AppModule::class, NavigationModule::class, LocalRepositoryModule::class, SchedulersModule::class, MainBindingModule::class],
    dependencies = [RemoteRepositoryComponent::class]
)
interface AppComponent : AndroidInjector<App> {
    override fun inject(app: App)

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>() {
        abstract fun remoteRepositoryComponent(component: RemoteRepositoryComponent): Builder
    }
}
