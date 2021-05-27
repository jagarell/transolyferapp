package net.transolyfer.transolyfergo.presenter.view;

/**
 * Created by admin on 05/04/2017.
 */

public interface RegisterView extends BaseView {
    void showLoading();
    void hideLoading();
    void registerSuccess();
    void validateCodeSucess(Object object);
    void syncSuccess();
    void getNationalitiesSuccess(Object object);
    void getEnterprisesSuccess(Object object);
    void getParameterSuccess(Object object);
    void showMessage(String message);
    void showMessageValidate(String message);
    void totalRegisterOffline(int totalRegisters);
    void validateImageView(int totalRegisters);
}
