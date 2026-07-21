package com.bsodsoftware.abraxas.engine.graphics.effects;

import java.util.Random;

public class ScreenShake {
    private final Random random = new Random();
    private float intensity;
    private int duration;
    private float offsetX;
    private float offsetY;

    public void shake(float intensity, int duration) {
        this.intensity = intensity;
        this.duration = duration;
    }

    public void update() {
        if (duration > 0) {
            offsetX = (random.nextFloat() * 2f - 1f) * intensity;
            offsetY = (random.nextFloat() * 2f - 1f) * intensity;

            intensity *= 0.9f; // damping
            duration--;
        } else {
            offsetX *= 0.8f;
            offsetY *= 0.8f;
            if (Math.abs(offsetX) < 0.01f) offsetX = 0;
            if (Math.abs(offsetY) < 0.01f) offsetY = 0;
        }
    }

    public Random getRandom() {
        return random;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }
}
