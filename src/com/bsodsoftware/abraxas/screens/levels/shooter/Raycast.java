package com.bsodsoftware.abraxas.screens.levels.shooter;

import com.bsodsoftware.abraxas.engine.GameStateManager;
import com.bsodsoftware.abraxas.engine.events.CollisionEngine;
import com.bsodsoftware.abraxas.engine.events.Event;
import com.bsodsoftware.abraxas.engine.graphics.Sprite;
import com.bsodsoftware.abraxas.engine.player.Player;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Raycast extends GameState {
    private Maps.MAPS currentMap;
    private GameStateManager gameStateManager;
    private int mapWidth = 15;
    private int mapHeight = 15;
    private BufferedImage image;
    private int[] pixels;
    private int[][] map;
    private List<Texture> textures;
    private Camera camera;
    private Renderer screen;
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 600;
    private Audio song;
    private boolean loaded = false;
    private Sprite sprite;
    private Sprite sword;
    private int currentFrame = 0;
    private int initialPosX = 500;
    private int initialPosY = 40;
    private int currentPosX = initialPosX;
    private int currentPosY = initialPosY;

    private Color hudColor;
    private Font hudFont;

    private Player player;

    HashMap<Maps.MAPS, ArrayList<com.bsodsoftware.abraxas.engine.events.Event>> eventsByLevel;
    ArrayList<Event> events;
    boolean event1active = false;
    private CollisionEngine collisionEngine;

    public Raycast(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void init() {
        initMap();
        initPlayer();
        initScreen();
        initSound();
        initHud();
        initEvents();
        initCollisionEngine();

        this.loaded = true;
    }

    private void initScreen() {
        this.image = new BufferedImage(this.WINDOW_WIDTH, this.WINDOW_HEIGHT, 1);
        this.pixels = ((DataBufferInt)this.image.getRaster().getDataBuffer()).getData();
        this.textures = Texture.getAvailableTextures();
        this.camera = new Camera(3.1D, 3.1D, 1.0D, 0.0D, 0.0D, -0.66D, this.player);
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
        player.setStamina(3);
        player.setHealth(100);
        player.setName("Maverick");
        player.setAttack(10);
        player.setState(Player.STATE.STANDING);
    }

    private void initHud() {
        hudColor = new Color(128, 0,0);
        hudFont = new Font("Century Gothic", Font.BOLD, 35);
    }

    private void initEvents() {
        events = new ArrayList<>();
        eventsByLevel = new HashMap<>();
        Event e = new Event(1L, 3.0D, 4.0D, 4.0D, 5.0D, true);
        events.add(e);
        eventsByLevel.put(Maps.MAPS.E1M1, events);
    }

    private void initCollisionEngine() {
        this.collisionEngine = new CollisionEngine(currentMap, eventsByLevel);
    }

    private void initMap() {
        this.map = Maps.getE1M1();
        this.currentMap = Maps.MAPS.E1M1;
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
        // TODO quizás separar estas weas en métodos
        if (loaded) {
            // Raycaster Renderer
            graphics.drawImage(this.image, 0, 0, this.image.getWidth(), this.image.getHeight(), (ImageObserver) null);
            // Weapon Renderer
            weaponBob();
            sword.draw(graphics);
            // HUD
            graphics.setFont(hudFont);
            hudColor = new Color(128, 0,0);
            graphics.setColor(hudColor);
            graphics.drawString("HEALTH: " + player.getHealth(), 80, 70);
            hudColor = new Color(0, 128,0);
            graphics.setColor(hudColor);
            graphics.drawString("Stamina: " + player.getStamina(), 80, 110);

            if (event1active) {
                executeEvent1(graphics);
            }
        }  else {
            showLoadingScreen(graphics);
        }
    }

    private void executeEvent1(Graphics2D graphics) {

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
    // TODO hacer un engine de animaciones porque lo vamos a usar pa todo
    private void weaponBob() {
        if (this.player.getState().equals(Player.STATE.WALKING)) {
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

    public Maps.MAPS getCurrentMap() {
        return currentMap;
    }

    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int[] getPixels() {
        return pixels;
    }

    public int[][] getMap() {
        return map;
    }

    public List<Texture> getTextures() {
        return textures;
    }

    public Camera getCamera() {
        return camera;
    }

    public Renderer getScreen() {
        return screen;
    }

    public int getWINDOW_WIDTH() {
        return WINDOW_WIDTH;
    }

    public int getWINDOW_HEIGHT() {
        return WINDOW_HEIGHT;
    }

    public Audio getSong() {
        return song;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Sprite getSword() {
        return sword;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public int getInitialPosX() {
        return initialPosX;
    }

    public int getInitialPosY() {
        return initialPosY;
    }

    public int getCurrentPosX() {
        return currentPosX;
    }

    public int getCurrentPosY() {
        return currentPosY;
    }

    public Color getHudColor() {
        return hudColor;
    }

    public Font getHudFont() {
        return hudFont;
    }

    public Player getPlayer() {
        return player;
    }
}
