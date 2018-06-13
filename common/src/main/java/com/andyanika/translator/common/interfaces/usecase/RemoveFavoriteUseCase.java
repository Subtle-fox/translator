package com.andyanika.translator.common.interfaces.usecase;

import io.reactivex.Completable;

public interface RemoveFavoriteUseCase {
    Completable run(Integer wordId);
}
