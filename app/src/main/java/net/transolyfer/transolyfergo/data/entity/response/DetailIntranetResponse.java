package net.transolyfer.transolyfergo.data.entity.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DetailIntranetResponse  implements Serializable {

    @SerializedName("Eventos")
    private List<EventResponse> eventList;
    @SerializedName("Consultora")
    private String consultingEnterprise;
    @SerializedName("Telefono")
    private String phoneNumber;
    @SerializedName("Direccion")
    private String address;
    @SerializedName("NroPieza")
    private int pieceNumber;

    public List<EventResponse> getEventList() {
        return eventList;
    }

    public void setEventList(List<EventResponse> eventList) {
        this.eventList = eventList;
    }

    public String getConsultingEnterprise() {
        return consultingEnterprise;
    }

    public void setConsultingEnterprise(String consultingEnterprise) {
        this.consultingEnterprise = consultingEnterprise;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPieceNumber() {
        return pieceNumber;
    }

    public void setPieceNumber(int pieceNumber) {
        this.pieceNumber = pieceNumber;
    }
}
