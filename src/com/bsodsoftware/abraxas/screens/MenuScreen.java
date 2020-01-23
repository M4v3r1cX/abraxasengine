package com.bsodsoftware.abraxas.screens;

import com.bsodsoftware.abraxas.engine.Background;
import com.bsodsoftware.abraxas.engine.GameStateManager;
import com.bsodsoftware.abraxas.states.GameState;

import java.awt.*;

public class MenuScreen extends GameState {
    private Background background;

    private int currentChoice = 0;
    private String[] options = {"Story Mode", "Arcade Mode", "Quit"};

    private Color titleColor;
    private Font titleFont;
    private Font font;

    public MenuScreen(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        try {
            background = new Background("/Background/menu.png", 1);
            background.setVector(-0.1, 0);;

            titleColor = new Color(128, 0,0);
            titleFont = new Font("Century Gothic", Font.PLAIN, 28);
            font = new Font("Arial", Font.PLAIN, 12);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        background.update();
    }

    @Override
    public void draw(Graphics2D graphics) {
        background.draw(graphics);

    }

    @Override
    public void onKeyPressed(int key) {

    }

    @Override
    public void onKeyReleased(int key) {

    }
}
