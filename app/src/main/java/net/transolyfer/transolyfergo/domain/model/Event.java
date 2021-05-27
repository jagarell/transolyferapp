package net.transolyfer.transolyfergo.domain.model;

import java.io.Serializable;

/**
 * Created by garymontengro on 22/03/17.
 */

public class Event implements Serializable{

    private int IdNationality;

    private String Name;

    private String flagFotoApp;

    private String flagObsApp;

    public int getIdNationality() {
        return IdNationality;
    }

    public void setIdNationality(int idNationality) {
        IdNationality = idNationality;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFlagFotoApp() {
        return flagFotoApp;
    }

    public void setFlagFotoApp(String flagFotoApp) {
        this.flagFotoApp = flagFotoApp;
    }

    public String getFlagObsApp() {
        return flagObsApp;
    }

    public void setFlagObsApp(String flagObsApp) {
        this.flagObsApp = flagObsApp;
    }
}
