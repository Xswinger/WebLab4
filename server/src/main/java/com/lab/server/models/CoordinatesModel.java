package com.lab.server.models;

import com.lab.server.entities.Coordinates;
import org.springframework.ui.Model;

public class CoordinatesModel {
    private Long id;
    private double x;
    private double y;
    private double r;
    private boolean result;

    public CoordinatesModel() {
    }

    public static CoordinatesModel toModel(Coordinates coordinates) {
        CoordinatesModel model = new CoordinatesModel();
        model.setId(coordinates.getId());
        model.setX(coordinates.getX());
        model.setY(coordinates.getY());
        model.setR(coordinates.getR());
        return model;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }
}
