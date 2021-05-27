package net.transolyfer.transolyfergo.presenter.view;

import net.transolyfer.transolyfergo.data.entity.response.DetailOrderResponse;

public interface DetailView extends BaseView {
    void showLoading();
    void hideLoading();
    void detailSuccess(Object object);
    void showMessage(String message);
}
