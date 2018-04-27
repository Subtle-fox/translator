package com.andyanika.translator.di.module;

import com.andyanika.translator.di.FragmentScope;
import com.andyanika.translator.ui.adapter.HistoryListAdapter;
import com.andyanika.translator.ui.view.FavoriteView;
import dagger.Module;
import dagger.Provides;

@Module
public class FavoriteFragmentModule {
	private FavoriteView view;

	public FavoriteFragmentModule(FavoriteView view) {
		this.view = view;
	}

	@Provides
	@FragmentScope
	public FavoriteView getView() {
		return view;
	}

	@Provides
	@FragmentScope
	public HistoryListAdapter getAdapter() {
		return new HistoryListAdapter();
	}
}
