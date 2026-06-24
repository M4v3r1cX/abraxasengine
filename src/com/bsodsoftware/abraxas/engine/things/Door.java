package com.bsodsoftware.abraxas.engine.things;

public class Door {
    private float x;
    private float y;
    public float openAmount;
    private boolean opening;

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

    public float getOpenAmount() {
        return openAmount;
    }

    public void setOpenAmount(float openAmount) {
        this.openAmount = openAmount;
    }

    public boolean isOpening() {
        return opening;
    }

    public void setOpening(boolean opening) {
        this.opening = opening;
    }
}
