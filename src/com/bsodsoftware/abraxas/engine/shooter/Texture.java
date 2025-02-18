package com.bsodsoftware.abraxas.engine.shooter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class Texture {
   public static Texture wireframe = new Texture("/text1.png", 64);
   public static Texture wireframe2 = new Texture("/text1.png", 64);
   public static Texture wireframe3 = new Texture("/text1.png", 64);
   public static Texture wireframe4 = new Texture("/text1.png", 64);
   public int[] pixels;
   private String location;
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
      ret.add(wireframe);
      ret.add(wireframe2);
      ret.add(wireframe3);
      ret.add(wireframe4);
      return ret;
   }
}
