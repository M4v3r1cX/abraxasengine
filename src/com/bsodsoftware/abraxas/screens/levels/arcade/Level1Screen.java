package com.bsodsoftware.abraxas.screens.levels.arcade;

import com.bsodsoftware.abraxas.GamePanel;
import com.bsodsoftware.abraxas.engine.GameStateManager;
import com.bsodsoftware.abraxas.engine.graphics.TileMap;
import com.bsodsoftware.abraxas.states.GameState;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class Level1Screen extends GameState {

    private GameStateManager gameStateManager;
    private TileMap tileMap;

    public Level1Screen(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        init();
    }

    @Override
    public void init() {
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Sprites/Tiles/grasstileset.gif");
        tileMap.loadMap("/Maps/level1-1.map");
        tileMap.setPosition(0,0);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0,0, GamePanel.WIDTH, GamePanel.HEIGHT);

        tileMap.draw(graphics);
    }

    @Override
    public void onKeyPressed(int key) {

    }

    @Override
    public void onKeyReleased(int key) {

    }

    @Override
    public void onClick(ActionEvent e) {
        // bruh
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
}
