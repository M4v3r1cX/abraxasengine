package com.bsodsoftware.abraxas.screens.levels.ludopath;

import com.bsodsoftware.abraxas.engine.GameStateManager;
import com.bsodsoftware.abraxas.engine.control.KeyInputEnum;
import com.bsodsoftware.abraxas.engine.graphics.Sprite;
import com.bsodsoftware.abraxas.states.GameState;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Ludo extends GameState {

    private int currentPlayerPos = 0;
    private int maxMapSpaces = 56;
    private int dado = 0;
    private GameStateManager gameStateManager;
    private Sprite bg;
    private Sprite textframe;
    private Sprite textframe2;
    private Font txtFont;
    private Font txtFont2;
    private Color txtColor;
    private boolean isWin;

    public Ludo(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        try {
            bg = new Sprite("/Background/backschool.png", 1);
            textframe = new Sprite("/textframe.png", 1);
            textframe.setPosition(0,-120);

            textframe2 = new Sprite("/textframe.png", 1);
            textframe2.setPosition(0,200);

            txtColor = new Color(255, 255,255);
            txtFont = new Font("Arial", Font.PLAIN, 50);
            txtFont2 = new Font("Arial", Font.PLAIN, 40);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D graphics) {
        // Esto era más fácil en unity :') mi motor vale callampa
        bg.draw(graphics);
        textframe.draw(graphics);
        textframe2.draw(graphics);

        graphics.setColor(txtColor);
        graphics.setFont(txtFont);
        graphics.drawString("Ludo po man", 250, 70);

        graphics.setFont(txtFont2);
        graphics.drawString("Así se ve, creo", 270, 120);

        graphics.drawString("Estai en el " + currentPlayerPos, 290, 370);
        graphics.drawString("Apreta Enter pa tirar el dado", 150, 420);
        if (dado > 0) {
            graphics.drawString("Te salió el " + dado, 300, 470);
        }

        if (isWin) {
            graphics.setFont(txtFont);
            graphics.setColor(new Color(0,0,0));
            graphics.drawString("GANASTE PERRA", 200, 300);
        }
    }

    @Override
    public void onKeyPressed(int key) {
        if (key == KeyInputEnum.M.getValue()) {
            gameStateManager.setState(GameStateManager.MENU);
        }

        if (key == KeyInputEnum.ESC.getValue()) {
            System.exit(0);
        }

        if (key == KeyInputEnum.ENTER.getValue()) {
            if (!isWin) {
                tirarDado();
            }
        }
    }

    @Override
    public void onKeyReleased(int key) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

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

    private void tirarDado() {
        dado = (int)(Math.random() * 6) + 1;
        currentPlayerPos += dado;

        if (currentPlayerPos >= maxMapSpaces) {
            currentPlayerPos = maxMapSpaces;
            isWin = true;
        }
    }
}
