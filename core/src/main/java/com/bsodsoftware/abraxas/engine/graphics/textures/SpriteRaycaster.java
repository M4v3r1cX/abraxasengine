package com.bsodsoftware.abraxas.engine.graphics.textures;

public class SpriteRaycaster {
    private int id;
    private double x;
    private double y;
    private int texture;
    private int spriteType;

    boolean solid;
    double radius;

    public SpriteRaycaster(double x, double y, int texture, boolean solid, double radius, int spriteType) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.solid = solid;
        this.radius = radius;
        this.spriteType = spriteType;
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

    public int getSpriteType() {
        return spriteType;
    }

    public void setSpriteType(int spriteType) {
        this.spriteType = spriteType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
