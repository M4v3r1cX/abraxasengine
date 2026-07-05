package com.bsodsoftware.abraxas.engine.entities;

import com.bsodsoftware.abraxas.engine.combat.Combatant;
import com.bsodsoftware.abraxas.engine.combat.EnemyAttack;
import com.bsodsoftware.abraxas.engine.graphics.textures.SpriteRaycaster;

import java.util.List;
import java.util.Stack;

public class Enemy implements Combatant {
    private String name;
    private int health;
    private Stack<EnemyAttack> attacks;
    private SpriteRaycaster sprite;
    private int quantity;   // Para que 1 monstruo en el mapa represente a un grupo

    public Enemy(String name, int health, Stack<EnemyAttack> attacks, SpriteRaycaster sprite, int quantity) {
        this.name = name;
        this.health = health;
        this.attacks = attacks;
        this.sprite = sprite;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Stack<EnemyAttack> getAttacks() {
        return attacks;
    }

    public void setAttacks(Stack<EnemyAttack> attacks) {
        this.attacks = attacks;
    }

    public SpriteRaycaster getSprite() {
        return sprite;
    }

    public void setSprite(SpriteRaycaster sprite) {
        this.sprite = sprite;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
