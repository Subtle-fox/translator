package com.andyanika.translator.di.module;

import android.support.v7.app.AppCompatActivity;
import dagger.Module;

@Module
public class MainActivityModule {
    private AppCompatActivity activity;

    public MainActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }
}
