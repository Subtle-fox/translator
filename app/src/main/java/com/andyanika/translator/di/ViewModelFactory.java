package com.andyanika.translator.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> mProviders;

    @Inject
    ViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providers) {
        mProviders = providers;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(Class<T> modelClass) {
        final Provider<ViewModel> provider = mProviders.get(modelClass);
        if (provider != null) {
            return (T) provider.get();
        }
        throw new IllegalArgumentException("No such provider for " + modelClass.getCanonicalName());
    }

}
