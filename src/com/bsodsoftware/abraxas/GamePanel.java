package com.bsodsoftware.abraxas;

import com.bsodsoftware.abraxas.engine.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    public static final int WIDTH = 320;        // Resolución qlia del año de la corneta
    public static final int HEIGHT = 240;
    public static final int SCALE = 3;

    private Thread gameThread;
    //private ControlHandler controlHandler;
    private boolean running;
    private int FPS = 60;                       // Corre mejor que el bayonetta de ps3
    private long gameTime = 1000 / FPS;

    private BufferedImage image;
    private Graphics2D graphics2d;

    private GameStateManager gameStateManager;

    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setFocusable(true);
        requestFocus();
        //controlHandler = new ControlHandler();
    }

    @Override
    public void run() {
        init();
        long start;
        long elapsed;
        long wait;

        while (running) {
            start = System.nanoTime();
            update();
            draw();
            drawToScreen();

            elapsed = System.nanoTime() - start;

            wait = gameTime - elapsed / 1000000;
            if (wait < 0) {
                wait = 5;
            }

            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void drawToScreen() {
        Graphics graphics = getGraphics();
        graphics.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        graphics.dispose();
    }

    private void draw() {
        gameStateManager.draw(graphics2d);
    }

    private void update() {
        gameStateManager.update();
    }

    private void init() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        graphics2d = (Graphics2D)image.getGraphics();
        running = true;
        gameStateManager = new GameStateManager();
    }

    public void addNotify(){
        super.addNotify();
        if (gameThread == null){
            gameThread = new Thread(this);
            addKeyListener(this);
            gameThread.start();
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        gameStateManager.keyPressed(keyEvent.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        gameStateManager.keyReleased(keyEvent.getKeyCode());
    }
}
;