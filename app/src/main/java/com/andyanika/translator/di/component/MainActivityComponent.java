package com.andyanika.translator.di.component;

import com.andyanika.translator.di.ActivityScope;
import com.andyanika.translator.di.module.*;
import com.andyanika.translator.ui.MainActivity;
import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {
        MainActivityModule.class,
        ViewModelModule.class,
        ViewModelFactoryModule.class
})
public interface MainActivityComponent {
    void inject(MainActivity activity);
    TranslationFragmentComponent plus(TranslationFragmentModule module);
    HistoryFragmentComponent plus(HistoryFragmentModule module);
    FavoriteFragmentComponent plus(FavoriteFragmentModule module);
}