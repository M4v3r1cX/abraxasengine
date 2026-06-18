package com.bsodsoftware.abraxas.screens.levels.shooter;

import com.bsodsoftware.abraxas.engine.GameStateManager;
import com.bsodsoftware.abraxas.engine.control.KeyInputEnum;
import com.bsodsoftware.abraxas.engine.events.CollisionEngine;
import com.bsodsoftware.abraxas.engine.events.Event;
import com.bsodsoftware.abraxas.engine.graphics.Sprite;
import com.bsodsoftware.abraxas.engine.graphics.raycaster.LightSource;
import com.bsodsoftware.abraxas.engine.graphics.raycaster.SpriteRaycaster;
import com.bsodsoftware.abraxas.engine.player.Player;
import com.bsodsoftware.abraxas.engine.shooter.Camera;
import com.bsodsoftware.abraxas.engine.shooter.Maps;
import com.bsodsoftware.abraxas.engine.shooter.SoftwareRenderer;
import com.bsodsoftware.abraxas.engine.graphics.Texture;
import com.bsodsoftware.abraxas.engine.sound.Audio;
import com.bsodsoftware.abraxas.states.GameState;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

public class Raycast extends GameState {
    private GameStateManager gameStateManager;
    private int mapWidth = 15;
    private int mapHeight = 15;
    private BufferedImage image;
    private int[] pixels;
    private int[][] map;
    private List<Texture> textures;
    private List<SpriteRaycaster> sprites;

    private Camera camera;
    private SoftwareRenderer screen;
    private int centerX;
    private int centerY;

    private final int WINDOW_WIDTH = 1280;
    private final int WINDOW_HEIGHT = 800;
    private Audio song;
    private boolean loaded = false;
    private Sprite sprite;
    private Sprite sword;
    private int currentFrame = 0;
    private final int initialPosX = 1000;
    private final int initialPosY = 250;
    private int currentPosX = initialPosX;
    private int currentPosY = initialPosY;

    private Color hudColor;
    private Font hudFont;
    private Font pauseFont;
    private Font pauseMenuFont;
    private Color maskColor;

    private Player player;
    ArrayList<Event> events;
    private CollisionEngine collisionEngine;

    private int currentChoice = 0;
    private String[] options = {"Continuar", "Salir"};

    public Raycast(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void init() {
        initMap();
        initCollisionEngine();
        initPlayer();
        initScreen();
        initSound();
        initHud();
        initEvents();

        this.loaded = true;
    }

    private void initScreen() {
        this.image = new BufferedImage(this.WINDOW_WIDTH, this.WINDOW_HEIGHT, 1);
        this.pixels = ((DataBufferInt)this.image.getRaster().getDataBuffer()).getData();
        this.textures = Texture.getAvailableTextures();
        this.sprites = getSprites();
        this.camera = new Camera(3.1D, 3.1D, 1.0D, 0.0D, 0.0D, -0.66D, this.player, this.collisionEngine, this.sprites);
        this.screen = new SoftwareRenderer(map, this.textures, this.mapWidth, this.mapHeight, this.WINDOW_WIDTH, this.WINDOW_HEIGHT, getLights());

        sword = new Sprite("/Sprites/Weapons/sword.png", 1);
        sword.setPosition(initialPosX, initialPosY);
    }

    private List<LightSource> getLights() {
        List<LightSource> lights = new ArrayList<>();

        lights.add(new LightSource(6.5,7.5, 2.0,2.0));

        return lights;
    }

    private List<SpriteRaycaster> getSprites() {
        List<SpriteRaycaster> sprites = new ArrayList<>();

        sprites.add(new SpriteRaycaster(5.5, 4.5, 5, true, 0.3));
        sprites.add(new SpriteRaycaster(6.5, 7.5, 5, true, 0.3));
        sprites.add(new SpriteRaycaster(6.5, 7.5, 7, false, 0.3));

        return sprites;
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

        pauseFont = new Font("Century Gothic", Font.BOLD, 40);
        maskColor = new Color(0,0,0,150);
        pauseMenuFont = new Font("Arial", Font.PLAIN, 25);
    }

    private void initEvents() {
        events = new ArrayList<>();
        Event e = new Event(1L, 3.0D, 4.0D, 4.0D, 5.0D, false);
        events.add(e);
    }

    private void initCollisionEngine() {
        this.collisionEngine = new CollisionEngine(events);
    }

    private void initMap() {
        this.map = Maps.getLightTest();
    }

    @Override
    public void update() {
        if (loaded) {
            this.sortSprites();
            this.screen.update(this.camera, this.pixels, this.sprites);
            this.camera.update(map);
            this.collisionEngine.checkForCollission(this.camera.getxPos(), this.camera.getyPos());
            this.checkForEvents();
            if (this.player.getState().equals(Player.STATE.STANDING)) {
                this.camera.setVieneDePausa(false);
            }
        }
    }

    @Override
    public void draw(Graphics2D graphics) {
        // Este es el método principal pa dibujar renderizar
        if (loaded) {
            renderRaycast(graphics);
            drawWeapon(graphics);
            drawHud(graphics);
        }  else {
            showLoadingScreen(graphics);
        }
    }

    private void checkForEvents() {
        for (Event e : events) {
            if (e.isActive()) {
                //eventInProgress = true;
                player.setState(Player.STATE.IN_EVENT);
                executeEvent(e.getId());
            } else {
                if (player.getState().equals(Player.STATE.IN_EVENT)) {
                    //player.setState(Player.STATE.STANDING);
                    //eventInProgress = false;
                }
            }
        }
    }

    private void executeEvent(Long id) {
        this.camera.stopPlayerMovement(Player.STATE.IN_EVENT);
        switch (id.intValue()) {
            case 1:
                System.out.println("Evento 1 en proceso");
                this.map = Maps.getE1M2();
                initScreen();
                this.events.get(0).setActive(false);
                this.player.setState(Player.STATE.STANDING);
                break;
            default:
                break;
        }
    }

    private void renderRaycast(Graphics2D graphics) {
        graphics.drawImage(this.image, 0, 0, this.image.getWidth(), this.image.getHeight(), (ImageObserver) null);
    }

    private void drawWeapon(Graphics2D graphics) {
        weaponBob();
        sword.draw(graphics);
    }

    private void showLoadingScreen(Graphics2D graphics) {
        if (sprite == null) {
            sprite = new Sprite("/Background/loading.jpg", 1);
        }
        sprite.draw(graphics);
    }

    private void drawHud(Graphics2D graphics) {
        graphics.setFont(hudFont);
        hudColor = new Color(128, 0,0);
        graphics.setColor(hudColor);
        graphics.drawString("HEALTH: " + player.getHealth(), 80, 70);
        hudColor = new Color(0, 128,0);
        graphics.setColor(hudColor);
        graphics.drawString("Stamina: " + player.getStamina(), 80, 110);
        if (player.getState().equals(Player.STATE.PAUSE)) {
            drawPause(graphics);
        }
    }

    private void drawPause(Graphics2D graphics) {
        graphics.setColor(maskColor);
        graphics.fillRect(0,0,this.WINDOW_WIDTH,this.WINDOW_HEIGHT);
        graphics.setColor(Color.WHITE);
        graphics.setFont(pauseFont);
        graphics.drawString("Pausa", getCenteredXString("Pausa", graphics, pauseFont), 200);


        graphics.setFont(pauseMenuFont);

        if (currentChoice == 0) {
            graphics.setColor(Color.RED);
        } else {
            graphics.setColor(Color.WHITE);
        }
        graphics.drawString(options[0], getCenteredXString(options[0], graphics, pauseMenuFont), 280);
        if (currentChoice == 1) {
            graphics.setColor(Color.RED);
        } else {
            graphics.setColor(Color.white);
        }
        graphics.drawString(options[1], getCenteredXString(options[1], graphics, pauseMenuFont), 310);
    }

    private void sortSprites() {
        sprites.sort((a, b) -> {
            double distA = (camera.getxPos() - a.getX())*(camera.getxPos() - a.getX()) + (camera.getyPos() - a.getY())*(camera.getyPos() - a.getY());
            double distB = (camera.getxPos() - b.getX())*(camera.getxPos() - b.getX()) + (camera.getyPos() - b.getY())*(camera.getyPos() - b.getY());
            return Double.compare(distB, distA);
        });
    }

    @Override
    public void onKeyPressed(int key) {
        if (!player.getState().equals(Player.STATE.IN_EVENT)) {
            this.camera.keyPressed(key);
            System.out.println("Key " + key + " pressed");
        }

        if (key == 77) {
            gameStateManager.setState(GameStateManager.MENU);
        }

        // Voy a ser bien honesto, voy a poner el código pa manejar el menú de pausa desde acá porque no se como más
        // hacerlo y tengo mucho sueño pa inventar algo más

        if (key == KeyInputEnum.UP_ARROW.getValue() || key == KeyInputEnum.W.getValue()) {
            currentChoice++;
            // this.cursor.play(); efecto de sonido
            if (currentChoice > 1) {
                currentChoice = 0;
            }
        }

        if (key == KeyInputEnum.DOWN_ARROW.getValue() || key == KeyInputEnum.S.getValue()) {
            // this.cursor.play(); efecto de sonido
            currentChoice--;
            if (currentChoice < 0) {
                currentChoice = 1;
            }
        }

        if (key == KeyInputEnum.ENTER.getValue()) {
            if (currentChoice == 1) {
                System.exit(0);
            } else {
                this.camera.keyPressed(KeyInputEnum.ESC.getValue());
            }
        }
    }

    private int getCenteredXString(String s, Graphics2D graphics, Font f) {
        int pos = 0;

        FontMetrics metrics = graphics.getFontMetrics(f);
        int width = metrics.stringWidth(s);
        pos = (this.WINDOW_WIDTH / 2) - (width / 2);

        return pos;
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

    @Override
    public void mouseMoved(MouseEvent e) {
        int deltaX = e.getX() - centerX;

    }



    // intento de animación pa hacer la wea tipo Doom
    // TODO hacer un engine de animaciones porque lo vamos a usar pa todo
    private void weaponBob() {
        if (this.player.isWalking()) {
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

    public SoftwareRenderer getScreen() {
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
