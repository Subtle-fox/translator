package com.andyanika.translator.di;

import com.andyanika.translator.common.interfaces.ScreenRouter;

import ru.terrakok.cicerone.Router;

public class ScreenRouterImpl implements ScreenRouter {
    private Router router;

    ScreenRouterImpl(Router router) {
        this.router = router;
    }

    @Override
    public void navigateTo(String screenKey, Object data) {
        router.navigateTo(screenKey, data);
    }

    @Override
    public void backTo(String screenKey) {
        router.backTo(screenKey);
    }
}
