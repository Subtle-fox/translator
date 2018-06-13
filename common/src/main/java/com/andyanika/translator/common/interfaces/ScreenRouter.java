package com.andyanika.translator.common.interfaces;

public interface ScreenRouter {
    void navigateTo(String screenKey, Object data);

    void backTo(String screenKey);
}
