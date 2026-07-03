package com.bsodsoftware.abraxas.engine.events;

import com.bsodsoftware.abraxas.engine.graphics.textures.SpriteRaycaster;

import java.util.List;

public class CollisionEngine {
    List<Event> eventsByLevel;
    //Event e = new Event(1L, 3.0D, 4.0D, 4.0D, 5.0D, true);

    public CollisionEngine(List<Event> eventsByLevel) {
        this.eventsByLevel = eventsByLevel;
    }

    public void checkForCollission(double playerPosX, double playerPosY) {
        if (eventsByLevel != null) {
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

    public boolean collidesWithSprite(double x, double y, List<SpriteRaycaster> sprites, double playerRadius) {
        boolean ret = false;
        for (SpriteRaycaster sprite : sprites) {
            if (!sprite.isSolid()) continue;        // Colisión con sprite, se puede usar pa disparar combate si es monstruo

            double dx = x - sprite.getX();
            double dy = y - sprite.getY();

            double dist = Math.sqrt(dx * dx + dy * dy);

            if (dist < (playerRadius + sprite.getRadius())) {
                ret = true;
            }
        }

        return ret;
    }
}
