package com.bsodsoftware.abraxas.engine.player;

public class Player {

    public enum STATE {
        IN_COMBAT, WALKING, IN_MENU, PAUSE, STANDING,    // ON THE EDGE OF THE CRATER WHERE THE PROFETS ONCE SAID
        IN_EVENT
    }

    private int health;
    private int armor;
    private int attack;
    private int stamina;
    private String name;
    private STATE state;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
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
}
