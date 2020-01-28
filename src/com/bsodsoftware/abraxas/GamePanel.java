package com.bsodsoftware.abraxas;

import com.bsodsoftware.abraxas.engine.ControlHandler;
import com.bsodsoftware.abraxas.engine.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private Thread gameThread;
    //private ControlHandler controlHandler;
    private boolean running;
    private int FPS = 60;
    private long gameTime = 1000 / FPS;

    private BufferedImage image;
    private Graphics2D graphics;

    private GameStateManager gameStateManager;

    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
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
        Graphics graphics2 = getGraphics();
        graphics2.drawImage(image, 0, 0, null);
        graphics2.dispose();
    }

    private void draw() {
        gameStateManager.draw(graphics);
    }

    private void update() {
        gameStateManager.update();
    }

    private void init() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        graphics = (Graphics2D)image.getGraphics();
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