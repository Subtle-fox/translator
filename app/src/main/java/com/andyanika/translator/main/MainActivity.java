package com.andyanika.translator.main;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.andyanika.translator.R;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import ru.terrakok.cicerone.NavigatorHolder;


public class MainActivity extends DaggerAppCompatActivity {
    @Inject
    MainActivityPresenter presenter;

    @Inject
    NavigatorHolder navigatorHolder;

    @Inject
    MainActivityNavigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(presenter);

        if (savedInstanceState == null) {
            presenter.navigateToTranslation();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        navigatorHolder.removeNavigator();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        navigator.back();
    }

    public int getFragmentContainerId() {
        return R.id.content_frame;
    }
}
