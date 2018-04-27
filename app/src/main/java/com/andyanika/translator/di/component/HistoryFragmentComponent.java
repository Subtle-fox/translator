package com.andyanika.translator.di.component;

import com.andyanika.translator.di.FragmentScope;
import com.andyanika.translator.di.module.HistoryFragmentModule;
import com.andyanika.translator.ui.HistoryFragment;
import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = {HistoryFragmentModule.class})
public interface HistoryFragmentComponent {
    void inject(HistoryFragment fragment);
}