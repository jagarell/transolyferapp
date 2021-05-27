package net.transolyfer.transolyfergo.domain.callback;

public interface DetailOrderCallback<T> {
    void onDetailOrderSuccess(T object);
    void onDetailOrderFailure(Exception e);
    void onMessageError(String message);
}
