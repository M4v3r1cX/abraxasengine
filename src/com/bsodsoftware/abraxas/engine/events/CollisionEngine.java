package com.bsodsoftware.abraxas.engine.events;

import com.bsodsoftware.abraxas.engine.shooter.Maps;

import java.util.ArrayList;
import java.util.HashMap;

public class CollisionEngine {
    HashMap<Maps.MAPS, ArrayList<Event>> eventsByLevel;
    private Maps.MAPS currentMap;

    public CollisionEngine(Maps.MAPS currentMap, HashMap<Maps.MAPS, ArrayList<com.bsodsoftware.abraxas.engine.events.Event>> eventsByLevel) {
        this.currentMap = currentMap;
        this.eventsByLevel = eventsByLevel;
    }

    private void checkForCollission(double playerPosX, double playerPosY) {
        ArrayList<Event> events = eventsByLevel.get(currentMap);
    }
}
