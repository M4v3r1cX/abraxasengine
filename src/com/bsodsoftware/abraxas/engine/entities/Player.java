package com.bsodsoftware.abraxas.engine.entities;

import com.bsodsoftware.abraxas.engine.combat.Combatant;
import com.bsodsoftware.abraxas.engine.graphics.textures.SpriteRaycaster;
import com.bsodsoftware.abraxas.engine.util.BackstoryFactory;
import com.bsodsoftware.abraxas.engine.util.WeaponFactory;

public class Player implements Combatant {
    public enum STATE {
        IN_COMBAT, WALKING, ROTATING, PAUSE, STANDING,    // ON THE EDGE OF THE CRATER WHERE THE PROFETS ONCE SAID
        IN_EVENT
    }

    private int health;
    private final double radius = 0.3;
    private String name;
    private STATE state;
    private Inventory inventory;
    private Equipment equipment;
    private Backstory backstory;
    private SpriteRaycaster currentEnemy;

    public Player() {
        setName("Maverick");
        setState(Player.STATE.STANDING);
        setBackstory(BackstoryFactory.getBackstories().get(0));
        setInventory(new Inventory());
        setEquipment(new Equipment());
        setHealth(backstory.getHealth());

        getEquipment().setWeapon(WeaponFactory.getSword());
    }

    public int getHealth() {
        return health;
    }

    @Override
    public void takeDamage(int damage) {
        this.health -= damage;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public double getRadius() {
        return radius;
    }

    public boolean isWalking() {
        return getState().equals(STATE.WALKING);
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Backstory getBackstory() {
        return backstory;
    }

    public void setBackstory(Backstory backstory) {
        this.backstory = backstory;
    }

    public SpriteRaycaster getCurrentEnemy() {
        return currentEnemy;
    }

    public void setCurrentEnemy(SpriteRaycaster currentEnemy) {
        this.currentEnemy = currentEnemy;
    }
}
