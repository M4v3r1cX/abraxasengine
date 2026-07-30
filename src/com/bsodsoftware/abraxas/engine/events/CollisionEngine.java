package com.bsodsoftware.abraxas.engine.events;

import com.bsodsoftware.abraxas.engine.graphics.textures.SpriteRaycaster;
import com.bsodsoftware.abraxas.engine.graphics.textures.SpriteRaycasterType;

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

    public SpriteRaycaster getCollidingSprite(double x, double y, List<SpriteRaycaster> sprites, double playerRadius) {
        SpriteRaycaster ret = null;
        for (SpriteRaycaster sprite : sprites) {
            if (!sprite.isSolid()) continue;

            double dx = x - sprite.getX();
            double dy = y - sprite.getY();

            double radius = playerRadius + sprite.getRadius();

            if (dx * dx + dy * dy < radius * radius) {
                ret = sprite;
                break;
            }
        }
        return ret;
    }

    public boolean collidesWithSprite(double x, double y, List<SpriteRaycaster> sprites, double playerRadius) {
        return getCollidingSprite(x, y, sprites, playerRadius) != null;
    }

    public boolean collidesWithMonster(double x, double y, List<SpriteRaycaster> sprites, double playerRadius) {
        SpriteRaycaster sprite = getCollidingSprite(x, y, sprites, playerRadius);   // Cuando intenta moverse hacia adelante, no puede porque ve que collidea, asique no se mueve. Pero al no moverse, no se registra el combate acá. Hay que ver como hacer la wea en el update de la cámara quizá xD
        return sprite != null && sprite.getSpriteType() == SpriteRaycasterType.MONSTER;
    }
}
