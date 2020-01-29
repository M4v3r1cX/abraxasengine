package com.bsodsoftware.abraxas.screens;

import com.bsodsoftware.abraxas.engine.graphics.Background;
import com.bsodsoftware.abraxas.engine.GameStateManager;
import com.bsodsoftware.abraxas.states.GameState;

import java.awt.*;
import java.awt.event.KeyEvent;

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
            background = new Background("/Background/Sigil.png", 1);
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

        graphics.setColor(titleColor);
        graphics.setFont(titleFont);
        graphics.drawString("Abraxas Engine", 80, 70);

        graphics.setFont(font);
        for (int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                graphics.setColor(Color.BLACK);
            } else {
                graphics.setColor(Color.RED);
            }
            graphics.drawString(options[i], 145, 140 + i * 15 );
        }
    }

    @Override
    public void onKeyPressed(int key) {
        switch (key) {
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_Z:
                select();
                break;
            case KeyEvent.VK_DOWN:currentChoice++;
                if (currentChoice == options.length) {
                    currentChoice = 0;
                }
                break;
            case KeyEvent.VK_UP:
                currentChoice--;
                if (currentChoice < 0) {
                    currentChoice = options.length - 1;
                }
                break;
        }
    }

    @Override
    public void onKeyReleased(int key) {

    }

    private void select() {
        switch (currentChoice) {
            case 0:
                gameStateManager.setState(GameStateManager.LEVEL1);
                break;
            case 1:
                // Help
                break;
            case 2:
                System.exit(0);
                break;
        }
    }
}
