package com.bsodsoftware.abraxas.engine.combat;

import com.bsodsoftware.abraxas.engine.entities.Enemy;
import com.bsodsoftware.abraxas.engine.entities.Item;
import com.bsodsoftware.abraxas.engine.entities.ItemType;
import com.bsodsoftware.abraxas.engine.entities.Player;

import java.util.Random;

public class BattleSystem {
    private Player player;
    private Enemy enemy;
    private Random random;

    public BattleSystem(Player player, Enemy enemy){
        this.player = player;
        this.enemy = enemy;
        this.random = new Random();
    }

    public void executeTurn(PlayerAttack playerAttack) {
        EnemyAttack attack = enemy.getAttacks().pop();
        Item accessory1 = player.getEquipment().getAccesory1();
        Item accessory2 = player.getEquipment().getAccesory2();
        int playerSpeed = player.getBackstory().getSpeed();
        playerSpeed += accessory1 != null && accessory1.getType() == ItemType.ACCESORY_SPEED ? accessory1.getMaxValue() : 0;
        playerSpeed += accessory2 != null && accessory2.getType() == ItemType.ACCESORY_SPEED ? accessory2.getMaxValue() : 0;
        if (playerSpeed >= attack.getSpeed()) {
            playerActs(playerAttack);
            if (enemy.isAlive()) {
                enemyActs(attack);
            }
        } else {
            enemyActs(attack);
            if (player.isAlive()) {
                playerActs(playerAttack);
            }
        }
    }

    private void playerActs(PlayerAttack playerAttack) {
        Item accessory1 = player.getEquipment().getAccesory1();
        Item accessory2 = player.getEquipment().getAccesory2();
        int damage = player.getBackstory().getAttack() + (random.nextInt(playerAttack.getMaxDamage() - playerAttack.getMinDamage()) + playerAttack.getMinDamage());
        damage += accessory1 != null && accessory1.getType() == ItemType.ACCESORY_ATTACK ? accessory1.getMaxValue() : 0;
        damage += accessory2 != null && accessory2.getType() == ItemType.ACCESORY_ATTACK ? accessory2.getMaxValue() : 0;

        enemy.takeDamage(damage);
    }

    private void enemyActs(EnemyAttack enemyAttack) {
        Item chestArmor = player.getEquipment().getChestArmor();
        Item headArmor = player.getEquipment().getHeadArmour();
        Item shield = player.getEquipment().getShield();
        Item accessory1 = player.getEquipment().getAccesory1();
        Item accessory2 = player.getEquipment().getAccesory2();
        int damage = enemyAttack.getDamage() - player.getBackstory().getArmour();
        damage -= chestArmor != null ? chestArmor.getMaxValue() : 0;
        damage -= headArmor != null ? headArmor.getMaxValue() : 0;
        damage -= shield != null ? shield.getMaxValue() : 0;
        damage -= accessory1 != null && accessory1.getType() == ItemType.ACCESORY_ARMOUR ? accessory1.getMaxValue() : 0;
        damage -= accessory2 != null && accessory2.getType() == ItemType.ACCESORY_ARMOUR ? accessory2.getMaxValue() : 0;
        player.takeDamage(damage);
    }
}
