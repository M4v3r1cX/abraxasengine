package com.bsodsoftware.abraxas.engine.world;

public class Room {
    private int x;
    private int y;
    private int weight;
    private int height;

    public Room(int x, int y, int weight, int height) {
        this.x = x;
        this.y = y;
        this.weight = weight;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCenterX() {
        return x + weight / 2;
    }

    public int getCenterY() {
        return y + height / 2;
    }

}
