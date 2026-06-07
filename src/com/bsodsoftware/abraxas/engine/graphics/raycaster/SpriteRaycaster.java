package com.bsodsoftware.abraxas.engine.graphics.raycaster;

public class SpriteRaycaster {
    private double x;
    private double y;
    private int texture;

    public SpriteRaycaster(double x, double y, int texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
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

    public int getTexture() {
        return texture;
    }

    public void setTexture(int texture) {
        this.texture = texture;
    }
}
