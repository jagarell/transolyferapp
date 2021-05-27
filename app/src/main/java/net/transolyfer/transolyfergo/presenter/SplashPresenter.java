package net.transolyfer.transolyfergo.presenter;

import net.transolyfer.transolyfergo.presenter.view.SplashView;

/**
 * Created by admin on 05/04/2017.
 */

public class SplashPresenter implements Presenter<SplashView> {

    private SplashView splashView;

    @Override
    public void attachedView(SplashView view) {
        splashView = view;
    }

    @Override
    public void detachView() {
        splashView = null;
    }


}
