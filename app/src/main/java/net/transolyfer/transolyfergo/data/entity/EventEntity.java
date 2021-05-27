package net.transolyfer.transolyfergo.data.entity;

import java.io.Serializable;

public class EventEntity implements Serializable{

    private int IdEvento;

    private String Descripcion;

    private String FlagFotoApp;

    private String FlagObsApp;

    public int getIdEvent() {
        return IdEvento;
    }

    public void setIdEvent(int idEvento) {
        IdEvento = idEvento;
    }

    public String getDescription() {
        return Descripcion;
    }

    public void setDescription(String descripcion) {
        Descripcion = descripcion;
    }

    public String getFlagFotoApp() {
        return FlagFotoApp;
    }

    public void setFlagFotoApp(String flagFotoApp) {
        FlagFotoApp = flagFotoApp;
    }

    public String getFlagObsApp() {
        return FlagObsApp;
    }

    public void setFlagObsApp(String flagObsApp) {
        FlagObsApp = flagObsApp;
    }
}
