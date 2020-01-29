package com.bsodsoftware.abraxas.engine;

import com.bsodsoftware.abraxas.screens.MenuScreen;
import com.bsodsoftware.abraxas.screens.levels.Level1Screen;
import com.bsodsoftware.abraxas.states.GameState;

import java.awt.*;
import java.util.ArrayList;

public class GameStateManager {
    private ArrayList<GameState> stateList;
    private int currentState;
    //private ControlHandler controlHandler;

    public static final int MENU = 0;
    public static final int LEVEL1 = 1;

    public GameStateManager() {
        stateList = new ArrayList<>();
        currentState = MENU;
        //this.controlHandler = controlHandler;
        stateList.add(new MenuScreen(this));
        stateList.add(new Level1Screen(this));
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

    public void keyPressed(int key) {
        stateList.get(currentState).onKeyPressed(key);
    }

    public void keyReleased(int key) {
        stateList.get(currentState).onKeyReleased(key);
    }
}
