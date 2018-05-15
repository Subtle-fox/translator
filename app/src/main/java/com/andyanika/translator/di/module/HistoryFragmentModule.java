package com.andyanika.translator.di.module;

import com.andyanika.translator.di.FragmentScope;
import com.andyanika.translator.features.history.HistoryListAdapter;
import com.andyanika.translator.features.history.HistoryView;
import com.andyanika.usecases.AddFavoriteUseCase;
import com.andyanika.usecases.RemoveFavoriteUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public class HistoryFragmentModule {
	private HistoryView view;

	public HistoryFragmentModule(HistoryView view) {
		this.view = view;
	}

	@Provides
	@FragmentScope
	public HistoryView getView() {
		return view;
	}

	@Provides
	@FragmentScope
	public HistoryListAdapter getAdapter(AddFavoriteUseCase addFavoriteUseCase, RemoveFavoriteUseCase removeFavoriteUseCase) {
		return new HistoryListAdapter(addFavoriteUseCase, removeFavoriteUseCase);
	}
}
