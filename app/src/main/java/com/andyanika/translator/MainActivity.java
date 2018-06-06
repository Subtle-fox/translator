package com.andyanika.translator;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

import com.andyanika.translator.di.MainActivityComponent;
import com.andyanika.translator.di.MainActivityModule;

import javax.inject.Inject;

import ru.terrakok.cicerone.NavigatorHolder;


public class MainActivity extends AppCompatActivity {
    private MainActivityComponent activityComponent;

    @Inject
    MainActivityPresenter presenter;

    @Inject
    NavigatorHolder navigatorHolder;

    @Inject
    MainActivityNavigator navigator;

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
        activityComponent = App.getAppComponent().plus(new MainActivityModule(this));
        activityComponent.inject(this);
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
