package com.bsodsoftware.abraxas.engine.util;

import com.bsodsoftware.abraxas.engine.entities.Item;
import com.bsodsoftware.abraxas.engine.entities.ItemType;
import com.bsodsoftware.abraxas.engine.graphics.textures.Sprite;
import com.bsodsoftware.abraxas.engine.graphics.textures.SpriteRaycaster;

public class WeaponFactory {
    public static Item getSword() {
        final int initialPosX = 1000;
        final int initialPosY = 250;
        Item sword = new Item();
        sword.setName("Sword");
        sword.setType(ItemType.WEAPON);
        Sprite weaponSprite = new Sprite("/Sprites/Weapons/sword.png", 1);
        weaponSprite.setPosition(initialPosX, initialPosY);
        sword.setItemSprite(weaponSprite);
        sword.setMaxValue(4);
        sword.setMinValue(2);
        sword.setEquiped(true);
        //falta el icon pero aun no hacemos la visualización del inventario

        return sword;
    }
}
