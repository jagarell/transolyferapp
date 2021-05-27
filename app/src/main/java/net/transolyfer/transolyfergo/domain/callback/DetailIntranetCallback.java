package net.transolyfer.transolyfergo.domain.callback;

public interface DetailIntranetCallback<T>  {
    void onEnterpriseSuccess(T object);
    void onEnterpriseFailure(Exception e);
    void onReportSuccess(T object);
    void onReportFailure(Exception e);
    void onMessageError(String message);
}
