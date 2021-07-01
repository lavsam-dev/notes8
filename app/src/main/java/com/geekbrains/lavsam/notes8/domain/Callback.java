package com.geekbrains.lavsam.notes8.domain;

public interface Callback<T> {

    void onSuccess(T result);
}
