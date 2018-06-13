package com.andyanika.translator.di;

import com.andyanika.translator.common.interfaces.ScreenRouter;
import com.andyanika.translator.common.scopes.ApplicationScope;

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
