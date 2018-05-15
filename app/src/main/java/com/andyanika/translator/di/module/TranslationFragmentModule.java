package com.andyanika.translator.di.module;

import com.andyanika.translator.common.LocalRepository;
import com.andyanika.translator.di.FragmentScope;
import com.andyanika.translator.features.history.HistoryListAdapter;
import com.andyanika.translator.features.translate.TranslationView;
import com.andyanika.usecases.AddFavoriteUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public class TranslationFragmentModule {
	private TranslationView view;

	public TranslationFragmentModule(TranslationView view) {
		this.view = view;
	}

	@Provides
	@FragmentScope
	public TranslationView getView() {
		return view;
	}
}
