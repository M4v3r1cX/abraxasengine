package com.bsodsoftware.abraxas.engine;

import com.bsodsoftware.abraxas.screens.MenuScreen;
import com.bsodsoftware.abraxas.screens.levels.cutscenes.Cutscene1Screen;
import com.bsodsoftware.abraxas.screens.levels.shooter.Raycast;
import com.bsodsoftware.abraxas.states.GameState;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GameStateManager {
    private final ArrayList<GameState> stateList;
    private int currentState;

    public static final int MENU = 0;
    public static final int CUTSCENE1 = 1;
    public static final int RAYCAST = 2;

    public GameStateManager() {
        stateList = new ArrayList<>();
        currentState = MENU;
        stateList.add(new MenuScreen(this));
        stateList.add(new Cutscene1Screen(this));
        stateList.add(new Raycast(this));
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

    public void mouseClicked(MouseEvent e) {
        stateList.get(currentState).mouseClicked(e);
    }

    public void mouseEntered(MouseEvent e) {
        stateList.get(currentState).mouseEntered(e);
    }
}
