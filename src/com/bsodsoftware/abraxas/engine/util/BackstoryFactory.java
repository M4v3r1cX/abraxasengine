package com.bsodsoftware.abraxas.engine.util;

import com.bsodsoftware.abraxas.engine.entities.Backstory;

import java.util.ArrayList;
import java.util.List;

public class BackstoryFactory {
    private static List<Backstory> backstories;

    public static List<Backstory> getBackstories() {
        if (backstories == null) {
            buildBackstories();
        }
        return backstories;
    }

    private static void buildBackstories() {
        backstories = new ArrayList<>();
        Backstory b = new Backstory();
        b.setName("Normie");
        b.setDescription("Todo normal. No sobresales en nada. Ataque normal, velocidad normal, vida normal.");
        b.setArmour(0);
        b.setHealth(100);
        b.setAttack(1);
        b.setSpeed(3);
        backstories.add(b);
    }


}
