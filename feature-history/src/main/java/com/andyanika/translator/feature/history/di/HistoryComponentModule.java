package com.andyanika.translator.feature.history.di;

import android.support.v4.app.Fragment;

import com.andyanika.translator.feature.history.HistoryFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = HistoryFragmentComponent.class)
public abstract class HistoryComponentModule {
    @Binds
    @IntoMap
    @FragmentKey(HistoryFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment>
    bindFragmentInjectorFactory(HistoryFragmentComponent.Builder builder);
}