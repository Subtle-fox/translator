package com.andyanika.translator.common.interfaces.usecase;

import io.reactivex.Completable;

public interface AddFavoriteUseCase {
    Completable run(Integer wordId);
}
