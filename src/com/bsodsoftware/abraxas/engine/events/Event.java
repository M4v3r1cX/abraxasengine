package com.bsodsoftware.abraxas.engine.events;

public class Event {
    public enum TYPE {
        PICKUP, DOOR, TELEPORTER, FIGHT, DIALOG
    }
    private Long id;
    private double xStart;
    private double yStart;
    private double xEnd;
    private double yEnd;
    private boolean active;
    private boolean hasBeenActivated;
    private boolean repeatable;

    public Event(Long id, double xStart, double yStart, double xEnd, double yEnd, boolean repeatable) {
        this.id = id;
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.repeatable = repeatable;
        this.hasBeenActivated = false;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public void setRepeatable(boolean repeatable) {
        this.repeatable = repeatable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getxStart() {
        return xStart;
    }

    public void setxStart(double xStart) {
        this.xStart = xStart;
    }

    public double getyStart() {
        return yStart;
    }

    public void setyStart(double yStart) {
        this.yStart = yStart;
    }

    public double getxEnd() {
        return xEnd;
    }

    public void setxEnd(double xEnd) {
        this.xEnd = xEnd;
    }

    public double getyEnd() {
        return yEnd;
    }

    public void setyEnd(double yEnd) {
        this.yEnd = yEnd;
    }

    public boolean isHasBeenActivated() {
        return hasBeenActivated;
    }

    public void setHasBeenActivated(boolean hasBeenActivated) {
        this.hasBeenActivated = hasBeenActivated;
    }
}
