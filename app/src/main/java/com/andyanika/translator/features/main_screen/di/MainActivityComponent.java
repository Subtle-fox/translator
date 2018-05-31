package com.andyanika.translator.features.main_screen.di;

import com.andyanika.translator.di.ActivityScope;
import com.andyanika.translator.di.component.SelectLanguageComponent;
import com.andyanika.translator.di.module.*;
import com.andyanika.translator.features.favorites.di.FavoriteFragmentComponent;
import com.andyanika.translator.features.favorites.di.FavoriteFragmentModule;
import com.andyanika.translator.features.history.di.HistoryFragmentComponent;
import com.andyanika.translator.features.history.di.HistoryFragmentModule;
import com.andyanika.translator.features.translate.di.TranslationFragmentComponent;
import com.andyanika.translator.features.translate.di.TranslationFragmentModule;
import com.andyanika.translator.features.main_screen.MainActivity;
import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {
        MainActivityModule.class,
        ViewModelModule.class,
        ViewModelFactoryModule.class,
        ResourceModule.class
})
public interface MainActivityComponent {
    void inject(MainActivity activity);
    TranslationFragmentComponent plus(TranslationFragmentModule module);
    HistoryFragmentComponent plus(HistoryFragmentModule module);
    FavoriteFragmentComponent plus(FavoriteFragmentModule module);
    SelectLanguageComponent plus(SelectLanguageModule module);
}