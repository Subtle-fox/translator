package com.andyanika.usecases;

import com.andyanika.translator.common.interfaces.usecase.AddFavoriteUseCase;
import com.andyanika.translator.common.interfaces.usecase.GetFavoritesUseCase;
import com.andyanika.translator.common.interfaces.usecase.GetLanguagesUseCase;
import com.andyanika.translator.common.interfaces.usecase.GetSelectedLanguageUseCase;
import com.andyanika.translator.common.interfaces.usecase.HistoryUseCase;
import com.andyanika.translator.common.interfaces.usecase.RemoveFavoriteUseCase;
import com.andyanika.translator.common.interfaces.usecase.SelectLanguageUseCase;
import com.andyanika.translator.common.interfaces.usecase.TranslationUseCase;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class UseCaseModule {
    @Binds
    abstract TranslationUseCase provideTranslationUseCase(TranslateUseCaseImpl useCase);

    @Binds
    abstract SelectLanguageUseCase provideSelectLanguageUseCase(SelectLanguageUseCaseImpl useCase);

    @Binds
    abstract GetSelectedLanguageUseCase provideGetSelectedUseCase(GetSelectedLanguagesUseCaseImpl useCase);

    @Binds
    abstract GetLanguagesUseCase provideLanguagesUseCase(GetLanguagesUseCaseImpl useCase);

    @Binds
    abstract GetFavoritesUseCase provideGetFavoritesUseCase(GetFavoritesUseCaseImpl useCase);

    @Binds
    abstract RemoveFavoriteUseCase provideRemoveFavoritesUseCase(RemoveFavoriteUseCaseImpl useCase);

    @Binds
    abstract AddFavoriteUseCase provideAddFavoritesUseCase(AddFavoriteUseCaseImpl useCase);

    @Binds
    abstract HistoryUseCase provideHistoryUseCase(HistoryUseCaseImpl useCase);
}
