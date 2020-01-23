package com.bsodsoftware.abraxas.engine;

import com.bsodsoftware.abraxas.states.GameState;

import java.awt.*;
import java.util.ArrayList;

public class GameStateManager {
    private ArrayList<GameState> stateList;
    private int currentState;
    private ControlHandler controlHandler;

    public static final int MENU = 0;
    public static final int LEVEL1 = 1;

    public GameStateManager(ControlHandler controlHandler) {
        stateList = new ArrayList<>();
        currentState = MENU;
        this.controlHandler = controlHandler;
        //stateList.add(new MenuState)
    }

    public void setState(int state) {
        currentState = state;
        stateList.get(currentState).init();
    }

    public void update() {
        stateList.get(currentState).update();
    }

    public void draw(Graphics2D graphics) {
        stateList.get(currentState).draw(graphics);
    }
}
