package com.bsodsoftware.abraxas.engine.events;

import com.bsodsoftware.abraxas.engine.shooter.Maps;

import java.util.ArrayList;
import java.util.HashMap;

// Coordinador de eventos de cuando el player colisiona con un evento
public class EventEngine {
    HashMap<Maps.MAPS, ArrayList<Event>> eventsByLevel;
    ArrayList<Event> events;

    public EventEngine() {
        this.eventsByLevel = new HashMap<>();
        this.events = new ArrayList<>();

        init();
    }

    private void init() {
        Event e = new Event(1L, 3.0D, 4.0D, 4.0D, 5.0D, true);
        events.add(e);
        eventsByLevel.put(Maps.MAPS.E1M1, events);
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<Event> getEventsByMap(Maps.MAPS map) {
        return eventsByLevel.get(map);
    }
}
