package net.transolyfer.transolyfergo.presenter;

/**
 * Created by admin on 05/04/2017.
 */

public interface Presenter<V> {

    void attachedView(V view);

    void detachView();
}
