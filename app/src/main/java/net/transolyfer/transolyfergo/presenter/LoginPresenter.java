package net.transolyfer.transolyfergo.presenter;

import net.transolyfer.transolyfergo.data.entity.raw.UserData;
import net.transolyfer.transolyfergo.domain.callback.UserCallback;
import net.transolyfer.transolyfergo.domain.interactor.UserInteractor;
import net.transolyfer.transolyfergo.domain.repository.UserRepository;
import net.transolyfer.transolyfergo.presenter.view.LoginView;
import net.transolyfer.transolyfergo.repository.UserRepositoryImpl;
import net.transolyfer.transolyfergo.repository.datasource.UserDataStoreFactory;

public class LoginPresenter implements Presenter<LoginView>, UserCallback {

    private LoginView loginView;
    private UserInteractor userInteractor;
    private UserRepository userRepository;

    public void doLogin(UserData userData) {
        loginView.showLoading();
        userInteractor.doLogin(userData, this);
    }

    @Override
    public void onLoginSuccess(Object object) {
        loginView.hideLoading();
        if (object.toString().contains("El")){
            loginView.showMessage(object.toString());
        }else{
            loginView.loginSuccess( object);
        }
    }

    @Override
    public void onLoginFailure(Exception e) {
        loginView.hideLoading();
    }

    @Override
    public void onMessageError(String message) {
        loginView.hideLoading();
        loginView.showMessage(message);

    }

    @Override
    public void attachedView(LoginView view) {
        loginView = view;
        userRepository = new UserRepositoryImpl(new UserDataStoreFactory(
                loginView.getContext()));
        userInteractor = new UserInteractor(userRepository);
    }

    @Override
    public void detachView() {
        loginView = null;
    }
}
