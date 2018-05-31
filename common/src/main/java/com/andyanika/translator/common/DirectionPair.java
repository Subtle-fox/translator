package com.andyanika.translator.common;

public class DirectionPair<T> {
    public T src;
    public T dst;

    public DirectionPair(T src, T dst) {
        this.src = src;
        this.dst = dst;
    }
}
