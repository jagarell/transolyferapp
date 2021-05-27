package net.transolyfer.transolyfergo.presenter.view;

public interface ReportIntranetView extends BaseView {
    void showLoading();
    void hideLoading();
    void getEnterprisesSuccess(Object object);
    void reportSuccess(Object object);
    void showMessage(String message);
    void showPhoto(String urlPhoto);
}
