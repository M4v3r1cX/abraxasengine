package com.bsodsoftware.abraxas.engine.things;

import com.bsodsoftware.abraxas.engine.graphics.raycaster.SpriteRaycaster;

import java.util.ArrayList;
import java.util.List;

public class Enemy {
    private String name;
    private int health;
    private List<EnemyAttack> attacks;
    private SpriteRaycaster sprite;
    private int quantity;   // Para que 1 monstruo en el mapa represente a un grupo

    public Enemy(String name, int health, List<EnemyAttack> attacks, SpriteRaycaster sprite, int quantity) {
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

    public void setHealth(int health) {
        this.health = health;
    }

    public List<EnemyAttack> getAttacks() {
        return attacks;
    }

    public void setAttacks(List<EnemyAttack> attacks) {
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

    public static class EnemyAttack {
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

    public static class EnemyAttackTypes {
        public static final int ATTACK = 0;
        public static final int SPECIAL_ATTACK = 1;
        public static final int SKILL = 2;
        public static final int EVADE = 3;
    }

    public static class EnemyFactory {
        public static Enemy buildPillar() {
            SpriteRaycaster sprite = new SpriteRaycaster(5.5, 4.5, 10, true, 0.3);
            List<EnemyAttack> attacks = new ArrayList<>();
            attacks.add(new EnemyAttack("Attack", EnemyAttackTypes.ATTACK, 10, 10));
            return new Enemy("Pilar", 10, attacks, sprite, 1);
        }
    }
}
