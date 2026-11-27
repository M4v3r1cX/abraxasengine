package com.bsodsoftware.abraxas.engine.combat;

public class PlayerAttack {
    private String name;
    private PlayerAttackType type;
    private int minDamage;
    private int maxDamage;
    private int hitChance;
    private int speed;


    public PlayerAttack() {
    }

    public PlayerAttack(String name, PlayerAttackType type, int minDamage, int maxDamage, int hitChance, int speed) {
        this.name = name;
        this.type = type;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.hitChance = hitChance;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerAttackType getType() {
        return type;
    }

    public void setType(PlayerAttackType type) {
        this.type = type;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public void setMinDamage(int minDamage) {
        this.minDamage = minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }

    public int getHitChance() {
        return hitChance;
    }

    public void setHitChance(int hitChance) {
        this.hitChance = hitChance;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
