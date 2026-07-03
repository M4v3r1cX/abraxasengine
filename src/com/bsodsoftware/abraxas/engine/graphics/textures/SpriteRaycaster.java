package com.bsodsoftware.abraxas.engine.graphics.textures;

public class SpriteRaycaster {
    private double x;
    private double y;
    private int texture;

    boolean solid;
    double radius;

    public SpriteRaycaster(double x, double y, int texture, boolean solid, double radius) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.solid = solid;
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

    public int getTexture() {
        return texture;
    }

    public void setTexture(int texture) {
        this.texture = texture;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
