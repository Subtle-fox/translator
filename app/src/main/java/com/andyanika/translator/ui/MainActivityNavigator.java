package com.andyanika.translator.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.andyanika.translator.di.ActivityScope;
import com.andyanika.translator.features.favorites.FavoriteFragment;
import com.andyanika.translator.features.history.HistoryFragment;
import com.andyanika.translator.features.translate.TranslationFragment;

import javax.inject.Inject;

import ru.terrakok.cicerone.android.SupportFragmentNavigator;
import ru.terrakok.cicerone.commands.Command;

@ActivityScope
public class MainActivityNavigator extends SupportFragmentNavigator {
    private final MainActivity activity;

    private TranslationFragment translationFragment;
    private HistoryFragment historyFragment;
    private FavoriteFragment favoriteFragment;

    @Inject
    MainActivityNavigator(MainActivity activity) {
        super(activity.getSupportFragmentManager(), activity.getFragmentContainerId());
        this.activity = activity;
    }

    @Override
    protected Fragment createFragment(String screenKey, Object data) {
        switch (screenKey) {
            case Screens.TRANSLATION_SCREEN:
                if (translationFragment == null) {
                    translationFragment = new TranslationFragment();
                }
                return translationFragment;
            case Screens.HISTORY_SCREEN:
                if (historyFragment == null) {
                    historyFragment = new HistoryFragment();
                }
                return historyFragment;
            case Screens.FAVORITES_SCREEN:
                if (favoriteFragment == null) {
                    favoriteFragment = new FavoriteFragment();
                }
                return favoriteFragment;
        }
        return null;
    }

    @Override
    protected void showSystemMessage(String message) {

    }

    @Override
    protected void exit() {
        activity.finish();
    }

    @Override
    protected void setupFragmentTransactionAnimation(
            Command command,
            Fragment currentFragment,
            Fragment nextFragment,
            FragmentTransaction fragmentTransaction) {
        int animationEnter = 0;
        int animationExit = 0;
        if (currentFragment != null) {
            animationEnter = android.R.anim.slide_in_left;
            animationExit = android.R.anim.slide_out_right;
        }
        fragmentTransaction.setCustomAnimations(animationEnter, animationExit);
    }
}