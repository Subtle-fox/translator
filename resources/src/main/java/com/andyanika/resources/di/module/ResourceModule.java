package com.andyanika.resources.di.module;

import android.content.Context;

import com.andyanika.resources.di.ActivityScope;
import com.andyanika.translator.common.interfaces.Resources;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ResourceModule {
    @ActivityScope
    @Provides
    Resources provideResources(@Named("activity") Context activityContext) {
        return new Resources() {
            @Override
            public String getString(int resourceId) {
                return activityContext.getString(resourceId);
            }

            @Override
            public String getString(String resourceName) {
                int id = activityContext.getResources().getIdentifier(resourceName, "string", activityContext.getPackageName());
                return getString(id);
            }
        };
    }
}
