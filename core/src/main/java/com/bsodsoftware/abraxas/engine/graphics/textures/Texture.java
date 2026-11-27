package com.bsodsoftware.abraxas.engine.graphics.textures;

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
    public static Texture barrel = new Texture("/Sprites/Textures/wolfenstein/barrel.png", 64);
    public static Texture colorstone = new Texture("/Sprites/Textures/wolfenstein/colorstone.png", 64);
    public static Texture greenlight = new Texture("/Sprites/Textures/wolfenstein/greenlight.png", 64);
    public static Texture gray = new Texture("/Sprites/Textures/gray.png", 64);
    public static Texture nightsky = new Texture("/Sprites/Textures/gray.png", 480);
    public static Texture pillar = new Texture("/Sprites/Textures/wolfenstein/pillar.png", 64);
    public static Texture imp = new Texture("/Sprites/Textures/imp.png", 64);
    public static Texture torch = new Texture("/Sprites/Things/torch.png", 64);

   private int[] pixels;
   private final String location;
   private final int size;

   public Texture(String location, int size) {
      this.location = location;
      this.size = size;
      this.pixels = new int[this.size * this.size];
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

    public int[] getPixels() {
        return pixels;
    }

    public void setPixels(int[] pixels) {
        this.pixels = pixels;
    }

    public String getLocation() {
        return location;
    }

    public int getSize() {
        return size;
    }

    public static List<Texture> getAvailableTextures() {
      List<Texture> ret = new ArrayList();
      ret.add(bluestone);
       ret.add(greystone);
       ret.add(redbrick);
       ret.add(wood);
       ret.add(barrel);
       ret.add(colorstone);
       ret.add(greenlight);
       ret.add(gray);
       ret.add(nightsky);
       ret.add(pillar);
       ret.add(imp);
       ret.add(torch);
       return ret;
   }
}
