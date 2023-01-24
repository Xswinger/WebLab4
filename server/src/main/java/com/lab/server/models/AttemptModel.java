package com.lab.server.models;

import com.lab.server.entities.Coordinates;

public class AttemptModel {
    private Double x;
    private Double y;
    private Double r;
    private Boolean result;

    public AttemptModel() {
    }

    public static AttemptModel toModel(Coordinates coordinates, boolean result) {
        AttemptModel model = new AttemptModel();
        model.setX(coordinates.getX());
        model.setY(coordinates.getY());
        model.setR(coordinates.getR());
        model.setResult(result);
        return model;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
}
