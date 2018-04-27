package com.andyanika.translator.network;

import com.andyanika.translator.common.RemoteRepository;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        NetworkModule.class,
        RemoteRepositoryModule.class
})
public interface NetworkComponent {
    RemoteRepository provides();
}
