package com.bsodsoftware.abraxas.engine.graphics.raycaster;

public class LightSource {
    private double x;
    private double y;
    private double intensity;
    private double radius;

    public LightSource(double x, double y, double intensity, double radius) {
        this.x = x;
        this.y = y;
        this.intensity = intensity;
        this.radius = radius;
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

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
