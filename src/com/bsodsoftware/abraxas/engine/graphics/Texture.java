package com.bsodsoftware.abraxas.engine.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class Texture {
   /*public static Texture wireframe = new Texture("/Sprites/Textures/brick1.jpg", 64);
   public static Texture wireframe2 = new Texture("/Sprites/Textures/brick2.jpg", 64);
   public static Texture wireframe3 = new Texture("/Sprites/Textures/brick3.jpg", 64);
   public static Texture wireframe4 = new Texture("/Sprites/Textures/brick4.jpg", 64);*/
    public static Texture bluestone = new Texture("/Sprites/Textures/wolfenstein/bluestone.png", 64);
    public static Texture greystone = new Texture("/Sprites/Textures/wolfenstein/greystone.png", 64);
    public static Texture redbrick = new Texture("/Sprites/Textures/wolfenstein/redbrick.png", 64);
    public static Texture wood = new Texture("/Sprites/Textures/wolfenstein/wood.png", 64);

   public int[] pixels;
   private final String location;
   public final int SIZE;

   public Texture(String location, int size) {
      this.location = location;
      this.SIZE = size;
      this.pixels = new int[this.SIZE * this.SIZE];
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
}
