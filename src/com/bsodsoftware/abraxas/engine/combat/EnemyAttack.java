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
}