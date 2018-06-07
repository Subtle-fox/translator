package com.andyanika.translator.main;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.andyanika.translator.R;
import com.andyanika.translator.main.di.MainActivityComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import ru.terrakok.cicerone.NavigatorHolder;


public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    private MainActivityComponent activityComponent;

    @Inject
    MainActivityPresenter presenter;

    @Inject
    NavigatorHolder navigatorHolder;

    @Inject
    MainActivityNavigator navigator;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingActivityInjector;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingActivityInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prepareComponent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(presenter);

        if (savedInstanceState == null) {
            presenter.navigateToTranslation();
        }
    }

    private void prepareComponent() {
        AndroidInjection.inject(this);
//        activityComponent = App.getAppComponent().plus(new MainActivityModule(this));
//        activityComponent.inject(this);
    }

    public MainActivityComponent getActivityComponent() {
        return activityComponent;
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
