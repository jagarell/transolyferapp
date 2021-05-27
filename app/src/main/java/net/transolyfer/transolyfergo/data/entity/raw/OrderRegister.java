package net.transolyfer.transolyfergo.data.entity.raw;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by garymontengro on 6/04/17.
 */

public class OrderRegister implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    @SerializedName("NroPedido")
    private String orderNumber;

    @DatabaseField
    @SerializedName("Id_Empresa")
    private int enterpriseId;

    @DatabaseField
    @SerializedName("Id_Evento")
    private int eventId;

    @DatabaseField
    @SerializedName("Observacion")
    private String observation;

    @DatabaseField
    @SerializedName("Id_Telefono")
    private String phoneId;

    @DatabaseField
    @SerializedName("FechaEvento")
    private String eventDate;

    @DatabaseField
    @SerializedName("Latitud")
    private String latitud;

    @DatabaseField
    @SerializedName("Longitud")
    private String longitud;

    @DatabaseField
    @SerializedName("ImagenCargo")
    private String imageCargo;

    @DatabaseField
    @SerializedName("ImagenReferencia")
    private String imageReference;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(int enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getImageCargo() {
        return imageCargo;
    }

    public void setImageCargo(String imageCargo) {
        this.imageCargo = imageCargo;
    }

    public String getImageReference() {
        return imageReference;
    }

    public void setImageReference(String imageReference) {
        this.imageReference = imageReference;
    }
}
