package com.andyanika.translator.repository.remote;

import com.andyanika.translator.common.interfaces.RemoteRepository;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        NetworkModule.class,
        YandexRepositoryModule.class
})
public interface RemoteRepositoryComponent {
    @Named("yandex")
    RemoteRepository injectRemoteRepository();
}