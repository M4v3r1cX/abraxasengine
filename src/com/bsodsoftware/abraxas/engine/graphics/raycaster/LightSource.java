package com.bsodsoftware.abraxas.engine.graphics.raycaster;

public class LightSource {
    private float x;
    private float y;
    private float intensity;
    private float radius;

    public LightSource(float x, float y, float intensity, float radius) {
        this.x = x;
        this.y = y;
        this.intensity = intensity;
        this.radius = radius;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
