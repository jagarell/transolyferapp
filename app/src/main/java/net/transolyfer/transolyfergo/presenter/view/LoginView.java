package net.transolyfer.transolyfergo.presenter.view;

public interface LoginView extends BaseView {
    void showLoading();
    void hideLoading();
    void loginSuccess(Object object);
    void showMessage(String message);
}