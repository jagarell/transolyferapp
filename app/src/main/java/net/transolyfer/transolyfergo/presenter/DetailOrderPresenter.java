package net.transolyfer.transolyfergo.presenter;

import net.transolyfer.transolyfergo.data.entity.raw.OrderDetail;
import net.transolyfer.transolyfergo.data.entity.response.DetailOrderResponse;
import net.transolyfer.transolyfergo.domain.callback.DetailOrderCallback;
import net.transolyfer.transolyfergo.domain.callback.RegisterCallback;
import net.transolyfer.transolyfergo.domain.interactor.DetailOrderInteractor;
import net.transolyfer.transolyfergo.domain.repository.DetailOrderRepository;
import net.transolyfer.transolyfergo.presenter.view.DetailView;
import net.transolyfer.transolyfergo.repository.DetailOrderRepositoryImpl;
import net.transolyfer.transolyfergo.repository.datasource.DetailOrderDataStoreFactory;

import java.util.List;

public class DetailOrderPresenter implements Presenter<DetailView>, DetailOrderCallback {

    private DetailView detailView;
    private DetailOrderInteractor detailOrderInteractor;
    private DetailOrderRepository detailOrderRepository;

    public void showDetail(OrderDetail orderDetail) {
        detailView.showLoading();
        detailOrderInteractor.showDetail(orderDetail, this);
    }

    @Override
    public void onDetailOrderSuccess(Object object) {
        detailView.hideLoading();
        if (object.toString().contains("El")){
            detailView.showMessage(object.toString());
        }else{
            detailView.detailSuccess( object);
        }
    }

    @Override
    public void onDetailOrderFailure(Exception e) {
        detailView.hideLoading();
    }

    @Override
    public void onMessageError(String message) {
        detailView.hideLoading();
        detailView.showMessage(message);
    }

    @Override
    public void attachedView(DetailView view) {
        detailView = view;
        detailOrderRepository = new DetailOrderRepositoryImpl(new DetailOrderDataStoreFactory(
                detailView.getContext()));
        detailOrderInteractor = new DetailOrderInteractor(detailOrderRepository);
    }

    @Override
    public void detachView() {
        detailView = null;
    }
}
