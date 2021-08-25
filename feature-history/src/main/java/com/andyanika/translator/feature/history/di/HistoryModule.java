package com.andyanika.translator.feature.history.di;
//
//import androidx.lifecycle.ViewModel;
//
//import com.andyanika.resources.ViewModelKey;
//import com.andyanika.translator.common.scopes.FragmentScope;
//import com.andyanika.translator.feature.history.HistoryViewModel;
//
//import dagger.Binds;
//import dagger.Module;
//import dagger.Provides;
//import dagger.multibindings.IntoMap;
//import io.reactivex.rxjava3.subjects.PublishSubject;
//import io.reactivex.rxjava3.subjects.Subject;
//
//@Module
//abstract class HistoryModule {
//    @Provides
//    @FragmentScope
//    static Subject<Integer> provideObserver() {
//        return PublishSubject.create();
//    }
//
//    @Binds
//    @IntoMap
//    @FragmentScope
//    @ViewModelKey(HistoryViewModel.class)
//    abstract ViewModel bindViewModel(HistoryViewModel viewModel);
//}
