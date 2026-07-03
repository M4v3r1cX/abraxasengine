package com.bsodsoftware.abraxas.engine.graphics.lights;

import java.util.Random;

public class LightSource {
    private float x;
    private float y;
    private float red;
    private float green;
    private float blue;
    private float intensity;
    private float radius;
    private float flickerValue = 1.0f;
    private float flickerTarget = 1.0f;
    private float flickerSpeed = 0.05f;

    public LightSource(float x, float y, float red, float green, float blue, float intensity, float radius) {
        this.x = x;
        this.y = y;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.intensity = intensity;
        this.radius = radius;
        this.flickerSpeed = 0.03f + new Random().nextFloat() * 0.08f;
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

    public float getRed() {
        return red;
    }

    public void setRed(float red) {
        this.red = red;
    }

    public float getGreen() {
        return green;
    }

    public void setGreen(float green) {
        this.green = green;
    }

    public float getBlue() {
        return blue;
    }

    public void setBlue(float blue) {
        this.blue = blue;
    }

    public float getFlickerValue() {
        return flickerValue;
    }

    public void setFlickerValue(float flickerValue) {
        this.flickerValue = flickerValue;
    }

    public float getFlickerTarget() {
        return flickerTarget;
    }

    public void setFlickerTarget(float flickerTarget) {
        this.flickerTarget = flickerTarget;
    }

    public float getFlickerSpeed() {
        return flickerSpeed;
    }

    public void setFlickerSpeed(float flickerSpeed) {
        this.flickerSpeed = flickerSpeed;
    }
}
