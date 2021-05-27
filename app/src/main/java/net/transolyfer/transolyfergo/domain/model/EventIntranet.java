package net.transolyfer.transolyfergo.domain.model;

import java.io.Serializable;

public class EventIntranet implements Serializable {

    private int idOrder;

    private String eventOrder;

    private String dateEventOrder;

    private String imageUrl;

    private String imageUrl2;

    private String iconImage;

    private String colorImage;

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public String getEventOrder() {
        return eventOrder;
    }

    public void setEventOrder(String eventOrder) {
        this.eventOrder = eventOrder;
    }

    public String getDateEventOrder() {
        return dateEventOrder;
    }

    public void setDateEventOrder(String dateEventOrder) {
        this.dateEventOrder = dateEventOrder;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getIconImage() {
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    public String getColorImage() {
        return colorImage;
    }

    public void setColorImage(String colorImage) {
        this.colorImage = colorImage;
    }

    public String getImageUrl2() {
        return imageUrl2;
    }

    public void setImageUrl2(String imageUrl2) {
        this.imageUrl2 = imageUrl2;
    }
}
