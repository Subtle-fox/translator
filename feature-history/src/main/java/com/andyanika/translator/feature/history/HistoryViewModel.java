package com.andyanika.translator.feature.history;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;

import com.andyanika.translator.common.interfaces.usecase.AddFavoriteUseCase;
import com.andyanika.translator.common.interfaces.usecase.HistoryUseCase;
import com.andyanika.translator.common.interfaces.usecase.RemoveFavoriteUseCase;
import com.andyanika.translator.common.models.FavoriteModel;
import com.andyanika.translator.common.scopes.FragmentScope;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

@FragmentScope
public class HistoryViewModel extends ViewModel {
    final MutableLiveData<List<FavoriteModel>> data = new MutableLiveData<>();
    final MutableLiveData<Boolean> showClearBtn = new MutableLiveData<>();

    private final HistoryUseCase historyUseCase;
    private final AddFavoriteUseCase addFavoriteUseCase;
    private final RemoveFavoriteUseCase removeFavoriteUseCase;

    private Disposable listDisposable;
    private Disposable itemClickDisposable;

    @Inject
    HistoryViewModel(HistoryUseCase historyUseCase, AddFavoriteUseCase addFavoriteUseCase, RemoveFavoriteUseCase removeFavoriteUseCase) {
        this.historyUseCase = historyUseCase;
        this.addFavoriteUseCase = addFavoriteUseCase;
        this.removeFavoriteUseCase = removeFavoriteUseCase;
        showClearBtn.setValue(false);
    }

    void subscribeSearch(Observable<CharSequence> searchTextObservable, int limit) {
        listDisposable = searchTextObservable
                .startWith("")
                .map(CharSequence::toString)
                .distinctUntilChanged(String::equals)
                .doOnNext(s -> showClearBtn.postValue(!TextUtils.isEmpty(s)))
                .flatMap(str -> historyUseCase.run(str, limit).toObservable())
                .subscribe(data::postValue);
    }

    void subscribeItemClick(Observable<FavoriteModel> observable) {
        itemClickDisposable = observable.flatMapCompletable(model -> {
            if (model.isFavorite) {
                return removeFavoriteUseCase.run(model.id)
                        .doOnComplete(() -> Timber.d("favorite removed"));
            } else {
                return addFavoriteUseCase.run(model.id)
                        .doOnComplete(() -> Timber.d("favorite added"));
            }
        }).subscribe();
    }

    void unsubscribe() {
        if (itemClickDisposable != null && !itemClickDisposable.isDisposed()) {
            itemClickDisposable.dispose();
        }
        if (listDisposable != null && !listDisposable.isDisposed()) {
            listDisposable.dispose();
        }
    }

    @Override
    protected void onCleared() {
        unsubscribe();
        super.onCleared();
    }
}
