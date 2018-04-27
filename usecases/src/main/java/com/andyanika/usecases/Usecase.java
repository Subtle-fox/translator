package com.andyanika.usecases;

public interface Usecase<TRequest, UResult> {
    UResult run(TRequest request);
}
