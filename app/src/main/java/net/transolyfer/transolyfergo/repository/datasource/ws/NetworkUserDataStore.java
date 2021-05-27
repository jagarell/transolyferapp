package net.transolyfer.transolyfergo.repository.datasource.ws;

import net.transolyfer.transolyfergo.data.entity.raw.UserData;
import net.transolyfer.transolyfergo.data.entity.response.UserResponse;
import net.transolyfer.transolyfergo.repository.RepositoryCallback;

public interface NetworkUserDataStore {
    void doLogin(UserData userData, RepositoryCallback repositoryCallback);
}
