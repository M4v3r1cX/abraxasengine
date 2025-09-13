package com.bsodsoftware.abraxas;

import com.bsodsoftware.abraxas.engine.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener {
    public static final int WIDTH = 800;        // Resolución qlia del año de la corneta
    public static final int HEIGHT = 600;
    public static final int SCALE = 1;

    private Thread gameThread;
    private boolean running;
    private int maxFPS = 60;                       // Corre mejor que el bayonetta de ps3
    private long lastFPS = 0;

    private BufferedImage image;
    private Graphics2D graphics2d;

    private GameStateManager gameStateManager;

    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setFocusable(true);
        requestFocus();
        addMouseListener(this);
    }

    @Override
    public void run() {
        init();

        while (running) {
            long time = System.currentTimeMillis();
            update();
            draw();
            drawToScreen();

            long now = System.currentTimeMillis();
            long elapsed = (now - time);
            long sleepTime = 1000 / maxFPS - elapsed;
            try {
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                    this.lastFPS = maxFPS;
                } else {
                    this.lastFPS = 1000 / elapsed;
                }
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
        graphics2d.setFont(new Font("Century Gothic", Font.PLAIN, 20));
        graphics2d.setColor(new Color(255, 255,255));
        graphics2d.drawString("FPS: " + lastFPS, 15, 25);
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

    @Override
    public void mouseClicked(MouseEvent e) {
        gameStateManager.mouseClicked(e);
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