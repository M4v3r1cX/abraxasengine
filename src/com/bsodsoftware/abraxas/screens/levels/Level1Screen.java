package com.bsodsoftware.abraxas.screens.levels;

import com.bsodsoftware.abraxas.engine.GameStateManager;
import com.bsodsoftware.abraxas.engine.graphics.Sprite;
import com.bsodsoftware.abraxas.states.GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Level1Screen extends GameState {
    private ArrayList<String> script = new ArrayList<>();
    private int currentPos = 0;
    private GameStateManager gameStateManager;
    private Font font;
    private Sprite sprite;
    private Sprite textframe;
    private Sprite character;

    public Level1Screen(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        try {
            sprite = new Sprite("/Background/backschool.png", 1);
            //sprite.setVector(-0.1, 0);
            textframe = new Sprite("/textframe.png", 1);
            //textframe.setVector(-0.1, 0);
            textframe.setPosition(100,300);
            character = new Sprite("/Sprites/monokuma.png", 1);
            character.setPosition(250, 114);

            font = new Font("Arial", Font.PLAIN, 24);
        } catch (Exception e) {
            e.printStackTrace();
        }

        script.add("UPUPUPU... y wea");
        script.add("This is the second message");
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
        textframe.draw(graphics);
        character.draw(graphics);

        graphics.setColor(Color.WHITE);
        graphics.setFont(font);
        if (currentPos <= script.size()) {
            graphics.drawString(script.get(currentPos), 30, 450);
        }
    }

    @Override
    public void onKeyPressed(int key) {
        if (key == KeyEvent.VK_ENTER) {
            currentPos++;
        }
    }

    @Override
    public void onKeyReleased(int key) {

    }
}
