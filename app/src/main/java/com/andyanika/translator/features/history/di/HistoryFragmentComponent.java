package com.andyanika.translator.features.history.di;

import com.andyanika.translator.di.FragmentScope;
import com.andyanika.translator.features.history.HistoryFragment;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = {
        HistoryFragmentModule.class,
        HistoryViewModelModule.class
})
public interface HistoryFragmentComponent {
    void inject(HistoryFragment fragment);
}