package com.bsodsoftware.abraxas.engine.events;

public class Event {
    private Long id;
    private double xStart;
    private double yStart;
    private double xEnd;
    private double yEnd;
    private boolean active;
    private boolean repeatable;

    public Event(Long id, double xStart, double yStart, double xEnd, double yEnd, boolean repeatable) {
        this.id = id;
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.repeatable = repeatable;
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
}
