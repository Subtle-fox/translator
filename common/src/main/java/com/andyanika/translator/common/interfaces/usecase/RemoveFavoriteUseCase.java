package com.andyanika.translator.common.interfaces.usecase;


import io.reactivex.rxjava3.core.Completable;

public interface RemoveFavoriteUseCase {
    Completable run(Integer wordId);
}
