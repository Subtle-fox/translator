package com.andyanika.translator.feature.history.di;

import com.andyanika.resources.di.FragmentScope;
import com.andyanika.resources.di.module.ViewModelFactoryModule;
import com.andyanika.translator.feature.history.HistoryFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@FragmentScope
@Subcomponent(modules = {
        HistoryFragmentModule.class,
        HistoryViewModelModule.class,
        ViewModelFactoryModule.class
})
public interface HistoryFragmentComponent extends AndroidInjector<HistoryFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<HistoryFragment> {

    }
}