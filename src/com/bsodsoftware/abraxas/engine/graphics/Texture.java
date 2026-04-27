package com.bsodsoftware.abraxas.engine.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class Texture {
    public static Texture bluestone = new Texture("/Sprites/Textures/wolfenstein/bluestone.png", 64);
    public static Texture greystone = new Texture("/Sprites/Textures/wolfenstein/greystone.png", 64);
    public static Texture redbrick = new Texture("/Sprites/Textures/wolfenstein/redbrick.png", 64);
    public static Texture wood = new Texture("/Sprites/Textures/wolfenstein/wood.png", 64);

    public static Texture barrel = new Texture("/Sprites/Textures/wolfenstein/barrel.png", 64, 3.0, 4.0);

    // Esta wea está re mala, hay que arreglarlo. En algún futuro. TODO arregla esta wea.


   private int[] pixels;
   private final String location;
   private final int SIZE;
   private double posX;
   private double posY;

   public Texture(String location, int size) {
      this.location = location;
      this.SIZE = size;
      this.pixels = new int[this.SIZE * this.SIZE];
      this.load();
   }

    public Texture(String location, int size, double posX, double posY) {
        this.location = location;
        this.SIZE = size;
        this.pixels = new int[this.SIZE * this.SIZE];
        this.posX = posX;
        this.posY = posY;
        this.load();
    }

   private void load() {
      try {
    	 System.out.println(this.getClass().getResource("/"));
         BufferedImage image = ImageIO.read(this.getClass().getResource(this.location));
         int w = image.getWidth();
         int h = image.getHeight();
         image.getRGB(0, 0, w, h, this.pixels, 0, w);
      } catch (IOException var4) {
         var4.printStackTrace();
      }

   }

   public static List<Texture> getAvailableTextures() {
      List<Texture> ret = new ArrayList();
      /*ret.add(wireframe);
      ret.add(wireframe2);
      ret.add(wireframe3);
      ret.add(wireframe4);*/
       ret.add(bluestone);
       ret.add(greystone);
       ret.add(redbrick);
       ret.add(wood);
       return ret;
   }

   public static List<Texture> getAvailableSprites() {
       List<Texture> ret = new ArrayList();

       ret.add(barrel);

       return ret;
   }

    public int[] getPixels() {
        return pixels;
    }

    public void setPixels(int[] pixels) {
        this.pixels = pixels;
    }

    public String getLocation() {
        return location;
    }

    public int getSIZE() {
        return SIZE;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }
}
