package com.andyanika.translator.di.module;

import android.content.Context;

import com.andyanika.translator.common.Resources;
import com.andyanika.resources.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ResourceModule {
    @ActivityScope
    @Provides
    Resources provideResources(Context activityContext) {
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
