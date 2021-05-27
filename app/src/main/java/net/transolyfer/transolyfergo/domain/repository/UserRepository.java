package net.transolyfer.transolyfergo.domain.repository;

import net.transolyfer.transolyfergo.data.entity.raw.UserData;
import net.transolyfer.transolyfergo.domain.callback.UserCallback;

public interface UserRepository {
    void doLogin(UserData userData, UserCallback userCallback);
}
