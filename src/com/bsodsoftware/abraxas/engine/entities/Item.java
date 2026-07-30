package com.bsodsoftware.abraxas.engine.entities;

import com.bsodsoftware.abraxas.engine.graphics.textures.Sprite;

public class Item {
    private String name;
    private int minValue;
    private int maxValue;
    private int type;
    private boolean equiped;
    private Sprite itemSprite;
    private Sprite icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isEquiped() {
        return equiped;
    }

    public void setEquiped(boolean equiped) {
        this.equiped = equiped;
    }

    public Sprite getIcon() {
        return icon;
    }

    public void setIcon(Sprite icon) {
        this.icon = icon;
    }

    public Sprite getItemSprite() {
        return itemSprite;
    }

    public void setItemSprite(Sprite itemSprite) {
        this.itemSprite = itemSprite;
    }
}
