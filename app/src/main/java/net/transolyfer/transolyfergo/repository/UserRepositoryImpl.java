package net.transolyfer.transolyfergo.repository;

import net.transolyfer.transolyfergo.data.entity.raw.UserData;
import net.transolyfer.transolyfergo.domain.callback.UserCallback;
import net.transolyfer.transolyfergo.domain.repository.UserRepository;
import net.transolyfer.transolyfergo.repository.datasource.UserDataStoreFactory;
import net.transolyfer.transolyfergo.repository.datasource.ws.NetworkUserDataStore;

public class UserRepositoryImpl implements UserRepository {

    private final UserDataStoreFactory userDataStoreFactory;

    public UserRepositoryImpl(UserDataStoreFactory userDataStoreFactory) {
        this.userDataStoreFactory = userDataStoreFactory;
    }

    @Override
    public void doLogin(UserData userData, final UserCallback userCallback) {
        final NetworkUserDataStore networkUserDataStore = this.userDataStoreFactory.createSource();
        networkUserDataStore.doLogin(userData, new RepositoryCallback() {
            @Override
            public void onSuccess(Object object) {
                userCallback.onLoginSuccess(object);
            }

            @Override
            public void onFailure(Exception e) {
                userCallback.onLoginFailure(e);
            }

            @Override
            public void onFailure(Throwable throwable) {
                userCallback.onLoginFailure(null);
            }

            @Override
            public void onMessageError(String message) {
                userCallback.onMessageError(message);
            }
        });
    }

}
