package com.andyanika.translator.feature.translate;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.andyanika.translator.common.interfaces.Resources;
import com.andyanika.translator.common.interfaces.usecase.GetSelectedLanguageUseCase;
import com.andyanika.translator.common.interfaces.usecase.SelectLanguageUseCase;
import com.andyanika.translator.common.interfaces.usecase.TranslationUseCase;
import com.andyanika.translator.common.models.ui.DisplayTranslateResult;
import com.andyanika.translator.common.scopes.FragmentScope;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

@FragmentScope
public class TranslationViewModel extends ViewModel {
    final MutableLiveData<String> srcLabel = new MutableLiveData<>();
    final MutableLiveData<String> dstLabel = new MutableLiveData<>();
    final MutableLiveData<CharSequence> srcString = new MutableLiveData<>();
    final MutableLiveData<CharSequence> dstString = new MutableLiveData<>();

    final MutableLiveData<Boolean> showClearBtn = new MutableLiveData<>();
    final MutableLiveData<Boolean> showProgress = new MutableLiveData<>();
    final MutableLiveData<Boolean> isOffline = new MutableLiveData<>();
    final MutableLiveData<Boolean> isError = new MutableLiveData<>();

    static final long DELAY = 1;

    private final TranslationUseCase translateUseCase;
    private final SelectLanguageUseCase selectLanguageUseCase;
    private final GetSelectedLanguageUseCase getSelectedLanguagesUseCase;
    private final Scheduler uiScheduler;
    private final Scheduler computationScheduler;
    private final PublishSubject<String> retrySubject;
    private final Resources resources;

    private Disposable searchDisposable;
    private Disposable languageDisposable;
    private Disposable swapDisposable;

    @Inject
    TranslationViewModel(TranslationUseCase translateUseCase,
                         GetSelectedLanguageUseCase getSelectedLanguagesUseCase,
                         SelectLanguageUseCase selectLanguageUseCase,
                         PublishSubject<String> retrySubject,
                         Resources resources,
                         @Named("ui") Scheduler uiScheduler,
                         @Named("computation") Scheduler computationScheduler) {
        this.translateUseCase = translateUseCase;
        this.getSelectedLanguagesUseCase = getSelectedLanguagesUseCase;
        this.selectLanguageUseCase = selectLanguageUseCase;
        this.resources = resources;
        this.uiScheduler = uiScheduler;
        this.retrySubject = retrySubject;
        this.computationScheduler = computationScheduler;
    }

    public void translate(@NonNull final String text) {
        retrySubject.onNext(text);
    }

    private void showProgress(CharSequence charSequence) {
        showClearBtn.postValue(false);
        isOffline.postValue(false);
        isError.postValue(false);

        showProgress.postValue(charSequence.length() > 0);
        if (charSequence.length() == 0) {
            dstString.postValue("");
        }
    }

    public void subscribe(Observable<CharSequence> searchTextObservable) {
        languageDisposable = getSelectedLanguagesUseCase
                .run()
                .subscribe(pair -> {
                    srcLabel.postValue(pair.getSrc());
                    dstLabel.postValue(pair.getDst());
                });

        searchDisposable = searchTextObservable
                .map(CharSequence::toString)
                .doOnNext(t -> Timber.d("received new text: %s", t))
                .distinctUntilChanged(String::equals)
                .mergeWith(retrySubject)
                .doOnNext(this::showProgress)
                .doOnNext(s -> Timber.d("before debounce: %s", s))
                .debounce(DELAY, TimeUnit.SECONDS, computationScheduler)
                .doOnNext(s -> Timber.d("after debounce: %s", s))
                .switchMap(translateUseCase::run)
                .doOnNext(r -> Timber.d("after switch map: %s", r))
                .observeOn(uiScheduler)
                .subscribe(this::processResult, this::processError);
    }

    private void processError(Throwable throwable) {
        throwable.printStackTrace();

        isError.postValue(true);
        dstString.postValue("");
        showProgress.postValue(false);
        showClearBtn.postValue(true);
        isOffline.postValue(true);
    }

    private void processResult(DisplayTranslateResult result) {
        Timber.d("process result: %s", result.textTranslated);
        if (result.isFound) {
            processFoundResult(result);
        } else {
            processEmptyResult(result);
        }
    }

    private void processFoundResult(DisplayTranslateResult result) {
        dstString.postValue(result.textTranslated);
        showProgress.postValue(false);
        showClearBtn.postValue(true);
        isOffline.postValue(result.isOffline);
    }

    private void processEmptyResult(DisplayTranslateResult result) {
        isError.postValue(result.isError);
        dstString.postValue(result.textTranslated);
        showProgress.postValue(false);
        showClearBtn.postValue(true);
        isOffline.postValue(false);
        if (result.isError) {
            dstString.postValue("");
        } else {
            dstString.postValue(resources.getString(R.string.translation_not_found));
        }
    }

    public void swapDirection() {
        dispose(swapDisposable);
        swapDisposable = selectLanguageUseCase
                .swap()
                .observeOn(uiScheduler)
                .subscribe(() -> {
                    dstString.postValue("");
                    srcString.postValue("");
                });
    }

    private void dispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    void unsubscribe() {
        dispose(searchDisposable);
        dispose(languageDisposable);
        dispose(swapDisposable);
    }

    @Override
    protected void onCleared() {
        unsubscribe();
        super.onCleared();
    }
}
