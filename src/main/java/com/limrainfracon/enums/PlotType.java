package com.limrainfracon.enums;

public enum PlotType {
    RESIDENTIAL("RESIDENTIAL"),
    RESIDENTIAL_L_TYPE("RESIDENTIAL_L_TYPE"),
    COMMERCIAL("COMMERCIAL");

    private final String type;

    PlotType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}