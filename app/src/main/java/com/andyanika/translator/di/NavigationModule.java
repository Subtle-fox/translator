package com.andyanika.translator.di;

import com.andyanika.translator.common.interfaces.ScreenRouter;
import com.andyanika.translator.common.scopes.ApplicationScope;
import com.github.terrakok.cicerone.Cicerone;
import com.github.terrakok.cicerone.NavigatorHolder;
import com.github.terrakok.cicerone.Router;

import dagger.Module;
import dagger.Provides;

/**
 * Created by terrakok 24.11.16
 */

@Module
public class NavigationModule {
    private Cicerone<Router> cicerone;

    NavigationModule() {
        cicerone = Cicerone.create();
    }

    @Provides
    @ApplicationScope
    Router provideCiceroneRouter() {
        return cicerone.getRouter();
    }

    @Provides
    @ApplicationScope
    NavigatorHolder provideNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    @Provides
    @ApplicationScope
    ScreenRouter provideScreenRouter(Router router) {
        return new ScreenRouterImpl(router);
    }
}
