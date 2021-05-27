package net.transolyfer.transolyfergo.data.entity.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EventResponse implements Serializable {

    @SerializedName("Id")
    private int idOrder;

    @SerializedName("Evento")
    private String eventOrder;

    @SerializedName("FechaEvento")
    private String dateEventOrder;

    @SerializedName("UrlImagen")
    private String imageUrl;

    @SerializedName("UrlImagen2")
    private String imageUrl2;

    @SerializedName("Icono")
    private String iconImage;

    @SerializedName("Color")
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
