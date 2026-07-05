package com.bsodsoftware.abraxas.engine.combat;

public class EnemyAttack {
    private String name;
    private int type;
    private int damage;
    private int speed;

    public EnemyAttack(String name, int type, int damage, int speed) {
        this.name = name;
        this.type = type;
        this.damage = damage;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}