package com.andyanika.translator.features.main_screen.di;

import com.andyanika.resources.di.ActivityScope;
import com.andyanika.translator.di.module.ResourceModule;
import com.andyanika.translator.di.module.ViewModelFactoryModule;
import com.andyanika.translator.features.history.di.HistoryFragmentComponent;
import com.andyanika.translator.features.history.di.HistoryFragmentModule;
import com.andyanika.translator.features.main_screen.MainActivity;
import com.andyanika.translator.features.select_lang.di.SelectLanguageComponent;
import com.andyanika.translator.features.select_lang.di.SelectLanguageModule;
import com.andyanika.translator.features.select_lang.di.SelectLanguageViewModelModule;
import com.andyanika.translator.features.translate.di.TranslationFragmentComponent;
import com.andyanika.translator.features.translate.di.TranslationFragmentModule;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {
        MainActivityModule.class,
        SelectLanguageViewModelModule.class,
        ViewModelFactoryModule.class,
        ResourceModule.class
})
public interface MainActivityComponent {
    void inject(MainActivity activity);
    TranslationFragmentComponent plus(TranslationFragmentModule module);
    HistoryFragmentComponent plus(HistoryFragmentModule module);
//    FavoriteFragmentComponent plus(FavoriteFragmentModule module);
    SelectLanguageComponent plus(SelectLanguageModule module);
}