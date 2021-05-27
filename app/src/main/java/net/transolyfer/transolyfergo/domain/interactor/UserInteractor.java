package net.transolyfer.transolyfergo.domain.interactor;

import net.transolyfer.transolyfergo.data.entity.raw.UserData;
import net.transolyfer.transolyfergo.domain.callback.UserCallback;
import net.transolyfer.transolyfergo.domain.repository.UserRepository;

public class UserInteractor {

    private UserRepository userRepository;

    public UserInteractor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void doLogin(UserData userData, final UserCallback userCallback) {
        userRepository.doLogin(userData, userCallback);
    }
}
