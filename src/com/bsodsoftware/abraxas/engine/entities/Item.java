package com.bsodsoftware.abraxas.engine.entities;

import com.bsodsoftware.abraxas.engine.graphics.textures.Sprite;
import com.bsodsoftware.abraxas.engine.graphics.textures.SpriteRaycaster;

public class Item {
    private String name;
    private int minValue;
    private int maxValue;
    private int type;
    private boolean equiped;
    private SpriteRaycaster itemSprite;
    private SpriteRaycaster icon;

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

    public SpriteRaycaster getItemSprite() {
        return itemSprite;
    }

    public void setItemSprite(SpriteRaycaster itemSprite) {
        this.itemSprite = itemSprite;
    }

    public SpriteRaycaster getIcon() {
        return icon;
    }

    public void setIcon(SpriteRaycaster icon) {
        this.icon = icon;
    }
}
