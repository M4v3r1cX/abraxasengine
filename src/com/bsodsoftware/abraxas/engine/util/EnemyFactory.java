package com.bsodsoftware.abraxas.engine.util;

import com.bsodsoftware.abraxas.engine.combat.EnemyAttack;
import com.bsodsoftware.abraxas.engine.combat.EnemyAttackType;
import com.bsodsoftware.abraxas.engine.entities.Enemy;
import com.bsodsoftware.abraxas.engine.graphics.textures.SpriteRaycaster;

import java.util.ArrayList;
import java.util.List;

public class EnemyFactory {
    public static Enemy buildPillar() {
        SpriteRaycaster sprite = new SpriteRaycaster(5.5, 4.5, 11, true, 0.3);
        List<EnemyAttack> attacks = new ArrayList<>();
        attacks.add(new EnemyAttack("Attack", EnemyAttackType.ATTACK, 10, 10));
        return new Enemy("Pilar", 10, attacks, sprite, 1);
    }
}