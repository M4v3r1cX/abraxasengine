package com.bsodsoftware.abraxas.states;

import com.bsodsoftware.abraxas.engine.GameStateManager;

import java.awt.*;

public abstract class GameState {
    protected GameStateManager gameStateManager;

    public abstract void init();
    public abstract void update();
    public abstract void draw(Graphics2D graphics);
    public abstract void onKeyPressed(int key);
    public abstract void onKeyReleased(int key);
}
