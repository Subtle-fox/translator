package com.andyanika.translator.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.andyanika.translator.R;
import com.andyanika.translator.common.constants.Screens;
import com.andyanika.translator.common.scopes.ActivityScope;
import com.andyanika.translator.feature.favorites.FavoriteFragment;
import com.andyanika.translator.feature.history.HistoryFragment;
import com.andyanika.translator.feature.select.SelectLanguageFragment;
import com.andyanika.translator.feature.translate.TranslationFragment;

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
            case Screens.TRANSLATION:
                if (translationFragment == null) {
                    translationFragment = new TranslationFragment();
                }
                return translationFragment;
            case Screens.HISTORY:
                if (historyFragment == null) {
                    historyFragment = new HistoryFragment();
                }
                return historyFragment;
            case Screens.FAVORITES:
                if (favoriteFragment == null) {
                    favoriteFragment = new FavoriteFragment();
                }
                return favoriteFragment;
            case Screens.SELECT_LANGUAGE: {
                return SelectLanguageFragment.create((String) data);
            }
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
    public void back() {
        activity.setTitle(R.string.app_name);
        super.back();
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
        fragmentTransaction.setCustomAnimations(animationEnter, animationExit, animationEnter, animationExit);
    }
}