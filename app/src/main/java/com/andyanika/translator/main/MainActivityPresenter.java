package com.andyanika.translator.main;

import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;

import com.andyanika.translator.R;
import com.andyanika.translator.common.constants.Screens;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

public class MainActivityPresenter implements BottomNavigationView.OnNavigationItemSelectedListener {
    private Router router;

    @Inject
    MainActivityPresenter(Router router) {
        this.router = router;
    }

    public void navigateToTranslation() {
        router.replaceScreen(Screens.TRANSLATION);
    }

    public void navigateToHistory() {
        router.replaceScreen(Screens.HISTORY);
    }

    public void navigateToFavorites() {
        router.replaceScreen(Screens.FAVORITES);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_translate:
                navigateToTranslation();
                return true;
            case R.id.navigation_history:
                navigateToHistory();
                return true;
            case R.id.navigation_favorite:
                navigateToFavorites();
                return true;
        }
        return false;
    }
}
