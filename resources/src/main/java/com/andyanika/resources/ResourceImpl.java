package com.andyanika.resources;

import android.content.Context;

import com.andyanika.translator.common.interfaces.Resources;

import javax.inject.Inject;
import javax.inject.Named;

class ResourceImpl implements Resources {
    private Context activityContext;

    @Inject
    ResourceImpl(@Named("activity") Context activityContext) {
        this.activityContext = activityContext;
    }

    @Override
    public String getString(int resourceId) {
        return activityContext.getString(resourceId);
    }

    @Override
    public String getString(String resourceName) {
        int id = activityContext.getResources().getIdentifier(resourceName, "string", activityContext.getPackageName());
        return getString(id);
    }
}