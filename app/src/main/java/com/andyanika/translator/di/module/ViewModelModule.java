package com.andyanika.translator.di.module;

import android.arch.lifecycle.ViewModel;
import com.andyanika.translator.di.ViewModelKey;
import com.andyanika.translator.ui.vm.FavoritesViewModel;
import com.andyanika.translator.ui.vm.HistoryViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel.class)
    abstract ViewModel bindHistoryViewModel(HistoryViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel.class)
    abstract ViewModel bindFavoritesViewModel(FavoritesViewModel viewModel);
}
