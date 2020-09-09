package com.bsodsoftware.abraxas.engine;

import com.bsodsoftware.abraxas.screens.MenuScreen;
import com.bsodsoftware.abraxas.screens.levels.arcade.Level1Screen;
import com.bsodsoftware.abraxas.screens.levels.cutscenes.Cutscene1Screen;
import com.bsodsoftware.abraxas.states.GameState;

import java.awt.*;
import java.util.ArrayList;

public class GameStateManager {
    private ArrayList<GameState> stateList;
    private int currentState;
    private Graphics graphics;
    //private ControlHandler controlHandler;

    public static final int MENU = 0;
    public static final int CUTSCENE1 = 1;
    public static final int LEVEL1 = 2;

    public GameStateManager() {
        stateList = new ArrayList<>();
        currentState = MENU;
        stateList.add(new MenuScreen(this));
        stateList.add(new Cutscene1Screen(this));
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
