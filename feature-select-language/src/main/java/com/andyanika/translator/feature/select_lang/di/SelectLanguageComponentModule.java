package com.andyanika.translator.feature.select_lang.di;

import android.support.v4.app.Fragment;

import com.andyanika.translator.feature.select_lang.SelectLanguageFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = SelectLanguageComponent.class)
public abstract class SelectLanguageComponentModule {
    @Binds
    @IntoMap
    @FragmentKey(SelectLanguageFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment>
    bindFragmentInjectorFactory(SelectLanguageComponent.Builder builder);
}