package net.transolyfer.transolyfergo.data.entity.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DetailOrderResponse implements Serializable {

    @SerializedName("Consultora")
    private String consultingEnterprise;
    @SerializedName("NroContacto")
    private String contactNumber;
    @SerializedName("Direccion")
    private String address;
    @SerializedName("Latitud")
    private String latitud;
    @SerializedName("Longitud")
    private String longitud;
    @SerializedName("NroPieza")
    private int pieceNumber;

    public String getConsultingEnterprise() {
        return consultingEnterprise;
    }

    public void setConsultingEnterprise(String consultingEnterprise) {
        this.consultingEnterprise = consultingEnterprise;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public int getPieceNumber() {
        return pieceNumber;
    }

    public void setPieceNumber(int pieceNumber) {
        this.pieceNumber = pieceNumber;
    }
}
