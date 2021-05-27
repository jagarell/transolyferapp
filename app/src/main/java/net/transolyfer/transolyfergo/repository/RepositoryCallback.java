package net.transolyfer.transolyfergo.repository;

/**
 * Created by Admin on 21/03/2017.
 */

public interface RepositoryCallback<T> {
    void onSuccess(T object);
    void onFailure(Exception e);
    void onFailure(Throwable throwable);
    void onMessageError(String message);
}
