package com.lab.server.payload;

public class JwtRequest {
    private Double x;
    private Double y;
    private Double r;
    private boolean isCanvasRequest;

    public JwtRequest(Double x, Double y, Double r, boolean isCanvasRequest) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.isCanvasRequest = isCanvasRequest;
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

    public boolean isCanvasRequest() {
        return isCanvasRequest;
    }

    public void setCanvasRequest(boolean canvasRequest) {
        isCanvasRequest = canvasRequest;
    }
}
