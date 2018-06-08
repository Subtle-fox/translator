package com.andyanika.translator.di;

import com.andyanika.translator.common.interfaces.ScreenRouter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

/**
 * Created by terrakok 24.11.16
 */

@Module
public class NavigationModule {
    private Cicerone<Router> cicerone;

    public NavigationModule() {
        cicerone = Cicerone.create();
    }

    @Provides
    @Singleton
    Router provideCiceroneRouter() {
        return cicerone.getRouter();
    }

    @Provides
    @Singleton
    NavigatorHolder provideNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    @Provides
    @Singleton
    ScreenRouter provideScreenRouter(Router router) {
        return new ScreenRouter() {
            @Override
            public void navigateTo(String screenKey, Object data) {
                router.navigateTo(screenKey, data);
            }

            @Override
            public void backTo(String screenKey) {
                router.backTo(screenKey);
            }
        };
    }
}
