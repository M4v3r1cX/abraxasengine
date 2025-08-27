package com.bsodsoftware.abraxas.engine.events;

import com.bsodsoftware.abraxas.engine.shooter.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CollisionEngine {
    List<Event> eventsByLevel;
    //Event e = new Event(1L, 3.0D, 4.0D, 4.0D, 5.0D, true);

    public CollisionEngine(List<Event> eventsByLevel) {
        this.eventsByLevel = eventsByLevel;
    }

    public void checkForCollission(double playerPosX, double playerPosY) {
        for (Event e : eventsByLevel) {
            if (playerPosX >= e.getxStart() && playerPosX <= e.getxEnd() && playerPosY >= e.getyStart()
                    && playerPosY <= e.getyEnd()) {
                if ((!e.isHasBeenActivated() || e.isRepeatable()) && !e.isActive()) {
                    e.setActive(true);
                    e.setHasBeenActivated(true);
                }
            }
        }
    }
}
