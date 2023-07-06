package com.bsodsoftware.abraxas.states;

import com.bsodsoftware.abraxas.engine.GameStateManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public abstract class GameState {
    protected GameStateManager gameStateManager;

    public abstract void init();
    public abstract void update();
    public abstract void draw(Graphics2D graphics);
    public abstract void onKeyPressed(int key);
    public abstract void onKeyReleased(int key);
    public abstract void mouseClicked(MouseEvent e);
    public abstract void mousePressed(MouseEvent e);
    public abstract void mouseReleased(MouseEvent e);
    public abstract void mouseEntered(MouseEvent e);
    public abstract void mouseExited(MouseEvent e);
}
