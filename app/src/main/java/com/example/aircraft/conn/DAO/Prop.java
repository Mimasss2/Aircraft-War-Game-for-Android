package com.example.aircraft.conn.DAO;

public class Prop {
    private int propId;
    private String propName;
    private int propCredit;
    String description;

    public Prop(int propId, String propName, int propCredit,String description) {
        this.propId = propId;
        this.propName = propName;
        this.propCredit = propCredit;
        this.description = description;
    }

    public int getPropId() {
        return propId;
    }

    public void setPropId(int propId) {
        this.propId = propId;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public int getPropCredit() {
        return propCredit;
    }

    public void setPropCredit(int propCredit) {
        this.propCredit = propCredit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Prop{" +
                "propId=" + propId +
                ", propName='" + propName + '\'' +
                ", propCredit=" + propCredit +
                ", description='" + description + '\'' +
                '}';
    }
}
