package com.andyanika.datasource.local;

import com.andyanika.translator.common.LocalRepository;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = LocalRepositoryModule.class)
public interface LocalRepositoryComponent {
    LocalRepository provides();
}
