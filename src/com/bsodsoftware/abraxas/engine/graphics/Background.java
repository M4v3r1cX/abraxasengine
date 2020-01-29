package com.bsodsoftware.abraxas.engine.graphics;

import com.bsodsoftware.abraxas.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Background {
    private BufferedImage image;

    private double x;
    private double y;
    private double deltaX;
    private double deltaY;
    private double moveScale;

    public Background(String res, double moveScale) { //
        try {
            image = ImageIO.read(getClass().getResourceAsStream(res));
            this.moveScale = moveScale;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setPosition(double x, double y) {
        this.x = (x * moveScale) % GamePanel.WIDTH;
        this.y = (y * moveScale) % GamePanel.HEIGHT;
    }

    public void setVector(double deltaX, double deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public void update() {
        //x += deltaX;
        //y += deltaY;
        // HIJO DE PUTA
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(image, (int)x, (int)y, null);
        if (x < 0) {
            graphics2D.drawImage(image, (int)x + GamePanel.WIDTH, (int)y, null);
        }
        if (x > 0) {
            graphics2D.drawImage(image, (int)x - GamePanel.WIDTH, (int)y, null);
        }
    }
}
