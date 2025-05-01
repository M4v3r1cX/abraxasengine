package com.bsodsoftware.abraxas.engine.events;

import com.bsodsoftware.abraxas.engine.shooter.Maps;

import java.util.ArrayList;

public class CollisionEngine {

    private EventEngine eventEngine;
    private Maps.MAPS currentMap;

    public CollisionEngine(EventEngine eventEngine, Maps.MAPS currentMap) {
        this.eventEngine = eventEngine;
        this.currentMap = currentMap;
    }

    private void checkForCollission(double playerPosX, double playerPosY) {
        ArrayList<Event> events = eventEngine.getEventsByMap(this.currentMap);
    }
}
