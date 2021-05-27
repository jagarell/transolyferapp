package net.transolyfer.transolyfergo.presenter;

import net.transolyfer.transolyfergo.data.entity.raw.OrderDetailIntranet;
import net.transolyfer.transolyfergo.data.mapper.EnterpriseListDataMapper;
import net.transolyfer.transolyfergo.domain.callback.DetailIntranetCallback;
import net.transolyfer.transolyfergo.domain.interactor.ReportIntranetInteractor;
import net.transolyfer.transolyfergo.domain.repository.DetailIntranetRepository;
import net.transolyfer.transolyfergo.presenter.view.ReportIntranetView;
import net.transolyfer.transolyfergo.repository.DetailIntranetRepositoryImpl;
import net.transolyfer.transolyfergo.repository.datasource.DetailIntranetDataStoreFactory;

public class ReportIntranetPresenter implements Presenter<ReportIntranetView>, DetailIntranetCallback {

    private ReportIntranetView reportIntranetView;
    private ReportIntranetInteractor reportIntranetInteractor;
    private DetailIntranetRepository detailIntranetRepository;

    public void showDetailIntranet(OrderDetailIntranet orderDetailIntranet) {
        reportIntranetView.showLoading();
        reportIntranetInteractor.getReportIntranet(orderDetailIntranet, this);
    }

    public void getEnterprises(){
        reportIntranetView.showLoading();
        reportIntranetInteractor.getEnterprises(this);
    }

    @Override
    public void onEnterpriseSuccess(Object object) {
        reportIntranetView.hideLoading();
        reportIntranetView.getEnterprisesSuccess(object);
    }

    @Override
    public void onEnterpriseFailure(Exception e) {
        reportIntranetView.hideLoading();
        reportIntranetView.showMessage("Lo sentimos, Ocurrio un error tratar de conectarse con el servidor. " +
                "Intente nuevamente");
    }

    @Override
    public void onReportSuccess(Object object) {
        reportIntranetView.hideLoading();
        if (object.toString().contains("El")){
            reportIntranetView.showMessage(object.toString());
        }else{
            reportIntranetView.reportSuccess(object);
        }
    }

    @Override
    public void onReportFailure(Exception e) {
        reportIntranetView.hideLoading();
    }

    @Override
    public void onMessageError(String message) {
        reportIntranetView.hideLoading();
        reportIntranetView.showMessage(message);
    }

    @Override
    public void attachedView(ReportIntranetView view) {
        reportIntranetView = view;
        detailIntranetRepository = new DetailIntranetRepositoryImpl(new DetailIntranetDataStoreFactory(
                reportIntranetView.getContext()), new EnterpriseListDataMapper());
        reportIntranetInteractor = new ReportIntranetInteractor(detailIntranetRepository);
    }

    @Override
    public void detachView() {
        reportIntranetView = null;
    }
}
