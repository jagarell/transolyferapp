package net.transolyfer.transolyfergo.domain.callback;

public interface UserCallback <T> {
    void onLoginSuccess(T object);
    void onLoginFailure(Exception e);
    void onMessageError(String message);
}