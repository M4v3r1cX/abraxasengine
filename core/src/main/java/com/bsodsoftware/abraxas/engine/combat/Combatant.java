package com.bsodsoftware.abraxas.engine.combat;

public interface Combatant {
    int getHealth();
    void takeDamage(int damage);
    boolean isAlive();
}
