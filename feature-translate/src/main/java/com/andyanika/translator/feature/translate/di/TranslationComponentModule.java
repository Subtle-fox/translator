package com.andyanika.translator.feature.translate.di;

import android.support.v4.app.Fragment;

import com.andyanika.translator.feature.translate.TranslationFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = TranslationFragmentComponent.class)
public abstract class TranslationComponentModule {
    @Binds
    @IntoMap
    @FragmentKey(TranslationFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment>
    bindFragmentInjectorFactory(TranslationFragmentComponent.Builder builder);
}