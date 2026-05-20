package com.bsodsoftware.abraxas.screens.levels.ludopath;

import com.bsodsoftware.abraxas.engine.GameStateManager;
import com.bsodsoftware.abraxas.engine.control.KeyInputEnum;
import com.bsodsoftware.abraxas.engine.graphics.Sprite;
import com.bsodsoftware.abraxas.states.GameState;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Ludo extends GameState {
    private int currentPlayerPos = 0;
    private int currentPlayer2Pos = 0;
    private int maxMapSpaces = 56;
    private int dado = 0;
    private int dado2 = 0;
    private GameStateManager gameStateManager;
    private Sprite bg;
    private Sprite textframe;
    private Sprite textframe2;
    private Sprite textframe3;
    private Font txtFont;
    private Font txtFont2;
    private Color txtColor;
    private boolean isWin;
    private boolean isLose;
    private boolean player2backtobase;
    private boolean playerBacktobase;

    public Ludo(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        try {
            bg = new Sprite("/Background/backschool.png", 1);
            textframe = new Sprite("/textframe.png", 1);
            textframe.setPosition(0,-180);

            textframe2 = new Sprite("/textframe.png", 1);
            textframe2.setPosition(0,300);

            textframe3 = new Sprite("/textframe.png", 1);
            textframe3.setPosition(0,60);

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
        textframe3.draw(graphics);

        graphics.setColor(txtColor);
        graphics.setFont(txtFont);
        graphics.drawString("Ludo po man", 250, 50);

        graphics.setFont(txtFont2);
        graphics.drawString("Así se ve, creo", 270, 100);

        graphics.drawString("Player 2 en el " + currentPlayer2Pos, 270, 220);
        if (dado2 > 0) {
            graphics.drawString("Le salió el " + dado2, 300, 270);
        }

        graphics.drawString("Player 1 en el " + currentPlayerPos, 270, 470);
        graphics.drawString("Apreta Enter pa tirar el dado", 150, 520);
        if (dado > 0) {
            graphics.drawString("Te salió el " + dado, 300, 570);
        }

        if (playerBacktobase || player2backtobase) {
            graphics.setColor(new Color(255,0,0));
            String mensaje = "";
            if (playerBacktobase) {
                mensaje = "PLAYER 1";
            }
            if (player2backtobase) {
                mensaje = "PLAYER 2";
            }
            mensaje += " A LA BASE";
            graphics.drawString(mensaje, 200, 320);
        }

        if (isWin || isLose) {
            graphics.setFont(txtFont);
            graphics.setColor(new Color(255,0,0));
            graphics.drawString((isWin ? "GANASTE" : (isLose ? "PERDISTE" : "")) + " PERRA", 200, 320);
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
            if (!isWin && !isLose) {
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
        player2backtobase = false;
        playerBacktobase = false;

        dado = (int)(Math.random() * 6) + 1;
        currentPlayerPos += dado;

        if (currentPlayerPos == currentPlayer2Pos) {
            currentPlayer2Pos = 0;
            player2backtobase = true;
            return;
        }

        dado2 = (int)(Math.random() * 6) + 1;
        currentPlayer2Pos += dado2;

        if (currentPlayer2Pos == currentPlayerPos) {
            currentPlayerPos = 0;
            playerBacktobase = true;
            return;
        }

        if (currentPlayerPos >= maxMapSpaces) {
            currentPlayerPos = maxMapSpaces;
            isWin = true;
            return;
        }

        if (currentPlayer2Pos >= maxMapSpaces) {
            currentPlayer2Pos = maxMapSpaces;
            isLose = true;
        }
    }
}
