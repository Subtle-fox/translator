package com.andyanika.translator.di.module;

import com.andyanika.translator.di.FragmentScope;
import com.andyanika.translator.ui.adapter.HistoryListAdapter;
import com.andyanika.translator.ui.view.HistoryView;
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
	public HistoryListAdapter getAdapter() {
		return new HistoryListAdapter();
	}
}
