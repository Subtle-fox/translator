package com.andyanika.translator.features.history.di;

import com.andyanika.translator.di.FragmentScope;
import com.andyanika.translator.features.history.HistoryView;

import dagger.Module;
import dagger.Provides;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

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
    public Subject<Integer> provideObserver() {
        return PublishSubject.create();
    }
}
