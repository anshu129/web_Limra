package com.limrainfracon.enums;

public enum PlotStatus {
    AVAILABLE("AVAILABLE"),
    HOLD("HOLD"),
    SOLD("SOLD");

    private String status;

    PlotStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}