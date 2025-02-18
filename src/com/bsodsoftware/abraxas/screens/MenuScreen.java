package com.bsodsoftware.abraxas.screens;

import com.bsodsoftware.abraxas.engine.graphics.Sprite;
import com.bsodsoftware.abraxas.engine.GameStateManager;
import com.bsodsoftware.abraxas.states.GameState;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MenuScreen extends GameState {
    private Sprite sprite;

    private int currentChoice = 0;
    private String[] options = {"Visual Novel", "Raycaster", "Quit"};

    private Color titleColor;
    private Font titleFont;
    private Font font;

    public MenuScreen(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        try {
            sprite = new Sprite("/Background/Sigil.png", 1);
            sprite.setVector(-0.1, 0);;

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
        sprite.update();
    }

    @Override
    public void draw(Graphics2D graphics) {
        sprite.draw(graphics);

        graphics.setColor(titleColor);
        graphics.setFont(titleFont);
        graphics.drawString("Abraxas Engine", 80, 70);

        graphics.setFont(font);

        if (currentChoice == 0) {
            graphics.setColor(Color.RED);
        } else {
            graphics.setColor(Color.BLACK);
        }
        graphics.drawString(options[0], 145, 140);
        if (currentChoice == 1) {
            graphics.setColor(Color.RED);
        } else {
            graphics.setColor(Color.BLACK);
        }
        graphics.drawString(options[1], 145, 170);
        if (currentChoice == 2) {
            graphics.setColor(Color.RED);
        } else {
            graphics.setColor(Color.BLACK);
        }
        graphics.drawString(options[2], 145, 200);
    }

    @Override
    public void onKeyPressed(int key) {
        // TODO esta wea debiera ir en un singleton pa no interpretar input en multithread
        switch (key) {
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_Z:
                select();
                break;
            case KeyEvent.VK_DOWN:
                currentChoice++;
                if (currentChoice > 2) {
                    currentChoice = 0;
                }
                break;
            case KeyEvent.VK_UP:
                currentChoice--;
                if (currentChoice < 0) {
                    currentChoice = 2;
                }
                break;
        }
    }

    @Override
    public void onKeyReleased(int key) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("(" + e.getX() + "," + e.getY() + ")");
        if (e.getX() > 135 && e.getX() < 245) {
            if (e.getY() > 130 && e.getY() < 150) {
                currentChoice = 0;
                select();
            }

            if (e.getY() > 153 && e.getY() < 180) {
                currentChoice = 1;
                select();
            }

            if (e.getY() > 183 && e.getY() < 205) {
                currentChoice = 2;
                select();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void select() {
        switch (currentChoice) {
            case 0:
                gameStateManager.setState(GameStateManager.CUTSCENE1);
                break;
            case 1:
                gameStateManager.setState(GameStateManager.RAYCAST);
                break;
            case 2:
                System.exit(0);
                break;
        }
    }
}
