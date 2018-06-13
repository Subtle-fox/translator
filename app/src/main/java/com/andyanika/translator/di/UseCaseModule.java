package com.andyanika.translator.di;

import com.andyanika.translator.common.interfaces.usecase.AddFavoriteUseCase;
import com.andyanika.translator.common.interfaces.usecase.GetFavoritesUseCase;
import com.andyanika.translator.common.interfaces.usecase.GetLanguagesUseCase;
import com.andyanika.translator.common.interfaces.usecase.GetSelectedLanguageUseCase;
import com.andyanika.translator.common.interfaces.usecase.HistoryUseCase;
import com.andyanika.translator.common.interfaces.usecase.RemoveFavoriteUseCase;
import com.andyanika.translator.common.interfaces.usecase.SelectLanguageUseCase;
import com.andyanika.translator.common.interfaces.usecase.TranslationUseCase;
import com.andyanika.usecases.AddFavoriteUseCaseImpl;
import com.andyanika.usecases.GetFavoritesUseCaseImpl;
import com.andyanika.usecases.GetLanguagesUseCaseImpl;
import com.andyanika.usecases.GetSelectedLanguagesUseCaseImpl;
import com.andyanika.usecases.HistoryUseCaseImpl;
import com.andyanika.usecases.RemoveFavoriteUseCaseImpl;
import com.andyanika.usecases.SelectLanguageUseCaseImpl;
import com.andyanika.usecases.TranslateUseCaseImpl;

import dagger.Binds;
import dagger.Module;

@Module
abstract class UseCaseModule {
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
