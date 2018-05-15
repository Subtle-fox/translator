package com.andyanika.translator.di.module;

import com.andyanika.translator.di.FragmentScope;
import com.andyanika.translator.features.favorites.FavoriteView;
import com.andyanika.translator.features.favorites.FavoritesListAdapter;
import com.andyanika.usecases.RemoveFavoriteUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public class FavoriteFragmentModule {
    private FavoriteView view;

    public FavoriteFragmentModule(FavoriteView view) {
        this.view = view;
    }

    @Provides
    @FragmentScope
    public FavoriteView getView() {
        return view;
    }

    @Provides
    @FragmentScope
    public FavoritesListAdapter getAdapter(RemoveFavoriteUseCase removeFavoriteUseCase) {
        return new FavoritesListAdapter(removeFavoriteUseCase);
    }
}
