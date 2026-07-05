package com.bsodsoftware.abraxas.engine.entities;

public class Equipment {
    private Item weapon;
    private Item headArmour;
    private Item shield;
    private Item chestArmor;
    private Item accesory1;
    private Item accesory2;

    public Item getWeapon() {
        return weapon;
    }

    public void setWeapon(Item weapon) {
        this.weapon = weapon;
    }

    public Item getHeadArmour() {
        return headArmour;
    }

    public void setHeadArmour(Item headArmour) {
        this.headArmour = headArmour;
    }

    public Item getShield() {
        return shield;
    }

    public void setShield(Item shield) {
        this.shield = shield;
    }

    public Item getChestArmor() {
        return chestArmor;
    }

    public void setChestArmor(Item chestArmor) {
        this.chestArmor = chestArmor;
    }

    public Item getAccesory1() {
        return accesory1;
    }

    public void setAccesory1(Item accesory1) {
        this.accesory1 = accesory1;
    }

    public Item getAccesory2() {
        return accesory2;
    }

    public void setAccesory2(Item accesory2) {
        this.accesory2 = accesory2;
    }
}
