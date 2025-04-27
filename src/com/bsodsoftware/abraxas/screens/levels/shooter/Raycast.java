package com.bsodsoftware.abraxas.screens.levels.shooter;

import com.bsodsoftware.abraxas.engine.GameStateManager;
import com.bsodsoftware.abraxas.engine.graphics.Sprite;
import com.bsodsoftware.abraxas.engine.playerobjects.Player;
import com.bsodsoftware.abraxas.engine.shooter.Camera;
import com.bsodsoftware.abraxas.engine.shooter.Maps;
import com.bsodsoftware.abraxas.engine.shooter.Renderer;
import com.bsodsoftware.abraxas.engine.graphics.Texture;
import com.bsodsoftware.abraxas.engine.sound.Audio;
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
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 600;
    private Audio song;
    private boolean loaded = false;
    private Sprite sprite;
    private Sprite sword;
    int currentFrame = 0;
    int initialPosX = 500;
    int initialPosY = 40;
    int currentPosX = initialPosX;
    int currentPosY = initialPosY;

    private Color hudColor;
    private Font hudFont;

    private Player player;

    public Raycast(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void init() {
        initScreen();
        initSound();
        initPlayer();
        initHud();

        this.loaded = true;
    }

    private void initScreen() {
        this.image = new BufferedImage(this.WINDOW_WIDTH, this.WINDOW_HEIGHT, 1);
        this.pixels = ((DataBufferInt)this.image.getRaster().getDataBuffer()).getData();
        this.textures = Texture.getAvailableTextures();
        this.camera = new Camera(4.5D, 4.5D, 1.0D, 0.0D, 0.0D, -0.66D);
        this.screen = new Renderer(map, this.textures, this.mapWidth, this.mapHeight, this.WINDOW_WIDTH, this.WINDOW_HEIGHT);

        sword = new Sprite("/Sprites/Weapons/sword.png", 1);
        sword.setPosition(initialPosX, initialPosY);
    }

    private void initSound() {
        this.song = new Audio("/Audio/1.wav", true);
        if (this.song.getStatus().equals(Audio.STATUS.STOPPED)) {
            this.song.play();
        }
    }

    private void initPlayer() {
        player = new Player();
        player.setArmor(100);
        player.setHealth(100);
        player.setName("Maverick");
        player.setAttack(10);
    }

    private void initHud() {
        hudColor = new Color(128, 0,0);
        hudFont = new Font("Century Gothic", Font.BOLD, 35);
    }

    @Override
    public void update() {
        if (loaded) {
            this.screen.update(this.camera, this.pixels);
            this.camera.update(map);
        }
    }

    @Override
    public void draw(Graphics2D graphics) {
        if (loaded) {
            // Raycaster Renderer
            graphics.drawImage(this.image, 0, 0, this.image.getWidth(), this.image.getHeight(), (ImageObserver) null);
            // Weapon Renderer
            weaponBob();
            sword.draw(graphics);
            // HUD
            graphics.setColor(hudColor);
            graphics.setFont(hudFont);
            graphics.drawString("HEALTH: " + player.getHealth(), 80, 70);
        }  else {
            showLoadingScreen(graphics);
        }
    }

    private void showLoadingScreen(Graphics2D graphics) {
        if (sprite == null) {
            sprite = new Sprite("/Background/loading.jpg", 1);
        }
        sprite.draw(graphics);
    }

    @Override
    public void onKeyPressed(int key) {
        this.camera.keyPressed(key);

        if (key == 77) {
            gameStateManager.setState(GameStateManager.MENU);
        }
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

    // intento de animación pa hacer la wea tipo Doom
    private void weaponBob() {
        if (camera.isMoving()) {
            if (currentFrame < 20) {
                currentPosX-= 1;
                currentPosY+= 1;
            } else if (currentFrame < 40) {
                currentPosX -= 1;
                currentPosY -= 1;
            } else if (currentFrame < 60) {
                currentPosX+= 1;
                currentPosY+= 1;
            } else if (currentFrame < 80) {
                currentPosX += 1;
                currentPosY -= 1;
            } else {
                currentFrame = 0;
            }

            currentFrame = currentFrame + 1;
        } else {
            if (currentFrame > 0) {
                if (currentPosX > initialPosX) {
                    currentPosX-= 1;
                } else {
                    currentPosX+= 1;
                }
                if (currentPosY > initialPosY) {
                    currentPosY-= 1;
                } else {
                    currentPosY+= 1;
                }
            }
        }
        sword.setPosition(currentPosX, currentPosY);
    }
}
