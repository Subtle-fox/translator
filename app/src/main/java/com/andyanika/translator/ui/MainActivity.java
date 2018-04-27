package com.andyanika.translator.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import com.andyanika.translator.App;
import com.andyanika.translator.R;
import com.andyanika.translator.di.component.MainActivityComponent;
import com.andyanika.translator.di.module.MainActivityModule;


public class MainActivity extends AppCompatActivity {
    private MainActivityComponent activityComponent;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_translate:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new TranslationFragment()).commit();
                    return true;
                case R.id.navigation_history:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new HistoryFragment()).commit();
                    return true;
                case R.id.navigation_favorite:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new FavoriteFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prepareComponent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new TranslationFragment()).commit();
        }
    }

    private void prepareComponent() {
        activityComponent = App.getAppComponent().plus(new MainActivityModule(this));
        activityComponent.inject(this);
    }

    public MainActivityComponent getActivityComponent() {
        return activityComponent;
    }
}
