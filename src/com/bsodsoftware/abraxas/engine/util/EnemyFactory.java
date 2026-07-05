package com.bsodsoftware.abraxas.engine.util;

import com.bsodsoftware.abraxas.engine.combat.EnemyAttack;
import com.bsodsoftware.abraxas.engine.combat.EnemyAttackType;
import com.bsodsoftware.abraxas.engine.entities.Enemy;
import com.bsodsoftware.abraxas.engine.graphics.textures.SpriteRaycaster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class EnemyFactory {
    public static Enemy buildImp() {
        SpriteRaycaster sprite = new SpriteRaycaster(5.5, 4.5, 11, true, 0.3);
        return new Enemy("Imp", 10, getAttacks(), sprite, 1);
    }

    public static Stack<EnemyAttack> getAttacks() {
        Stack<EnemyAttack> attacks = new Stack<>();
        attacks.add(new EnemyAttack("Basic Attack", EnemyAttackType.ATTACK, 10, 10));
        attacks.add(new EnemyAttack("Basic Attack", EnemyAttackType.ATTACK, 10, 10));
        attacks.add(new EnemyAttack("Basic Attack", EnemyAttackType.ATTACK, 10, 10));
        attacks.add(new EnemyAttack("Special Attack", EnemyAttackType.SPECIAL_ATTACK, 20, 20));
        attacks.add(new EnemyAttack("Special Attack", EnemyAttackType.SPECIAL_ATTACK, 20, 20));
        attacks.add(new EnemyAttack("Skill", EnemyAttackType.SKILL, 50, 20));
        attacks.add(new EnemyAttack("Evade", EnemyAttackType.EVADE, 0, 100));
        Collections.shuffle(attacks);
        return attacks;
    }
}