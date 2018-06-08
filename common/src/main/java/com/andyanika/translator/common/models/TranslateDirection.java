package com.andyanika.translator.common.models;

public class TranslateDirection<T> {
    public T src;
    public T dst;

    public TranslateDirection(T src, T dst) {
        this.src = src;
        this.dst = dst;
    }
}
