package com.bsodsoftware.abraxas.screens.levels.shooter;

import com.bsodsoftware.abraxas.engine.GameStateManager;
import com.bsodsoftware.abraxas.engine.shooter.Camera;
import com.bsodsoftware.abraxas.engine.shooter.Maps;
import com.bsodsoftware.abraxas.engine.shooter.Renderer;
import com.bsodsoftware.abraxas.engine.graphics.Texture;
import com.bsodsoftware.abraxas.states.GameState;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;
import java.util.List;

public class Raycast extends GameState {
    private GameStateManager gameStateManager;
    public int mapWidth = 15;
    public int mapHeight = 15;
    private BufferedImage image;
    public int[] pixels;
    public static int[][] map = Maps.getE1M1();
    public List<Texture> textures;
    public Camera camera;
    public Renderer screen;
    private int WINDOW_WIDTH = 800;
    private int WINDOW_HEIGHT = 600;

    public Raycast(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void init() {
        this.image = new BufferedImage(this.WINDOW_WIDTH, this.WINDOW_HEIGHT, 1);
        this.pixels = ((DataBufferInt)this.image.getRaster().getDataBuffer()).getData();
        this.textures = Texture.getAvailableTextures();
        this.camera = new Camera(4.5D, 4.5D, 1.0D, 0.0D, 0.0D, -0.66D);
        this.screen = new Renderer(map, this.textures, this.mapWidth, this.mapHeight, this.WINDOW_WIDTH, this.WINDOW_HEIGHT);
    }

    @Override
    public void update() {
        this.screen.update(this.camera, this.pixels);
        this.camera.update(map);
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.drawImage(this.image, 0, 0, this.image.getWidth(), this.image.getHeight(), (ImageObserver) null);
    }

    @Override
    public void onKeyPressed(int key) {
        this.camera.keyPressed(key);
    }

    @Override
    public void onKeyReleased(int key) {
        this.camera.keyReleased(key);
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
