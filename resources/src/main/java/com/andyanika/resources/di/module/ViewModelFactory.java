package com.andyanika.resources.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Inject;
import javax.inject.Provider;

import java.util.Map;

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
