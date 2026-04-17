package com.bsodsoftware.abraxas.engine.shooter;

import com.bsodsoftware.abraxas.engine.graphics.Texture;

import java.awt.Color;
import java.util.List;

public class Renderer {
   public int[][] map;
   public int mapWidth;
   public int mapHeight;
   public int width;
   public int height;
   public List<Texture> textures;
   private final int screenHeight = 600;
   private final int screenWidth = 800;

   public Renderer(int[][] map, List<Texture> textures, int mapWidth, int mapHeight, int width, int height) {
      this.map = map;
      this.textures = textures;
      this.mapWidth = mapWidth;
      this.mapHeight = mapHeight;
      this.width = width;
      this.height = height;
   }

   public int[] update(Camera camera, int[] pixels) {
      int x;
      for(x = 0; x < pixels.length / 2; ++x) {
         if (pixels[x] != Color.DARK_GRAY.getRGB()) {  // ceiling
            pixels[x] = Color.DARK_GRAY.getRGB();
         }
      }

      for(x = pixels.length / 2; x < pixels.length; ++x) {
         if (pixels[x] != Color.gray.getRGB()) { // floor
            pixels[x] = Color.gray.getRGB();
            //pixels[x] = 775024;
         }
      }

      // Floor casting
      /*for(int y = screenHeight / 2 + 1; y < screenHeight; ++y) {
         // rayDir for leftmost ray (x = 0) and rightmost ray (x = w)
         double rayDirX0 = camera.getxDir() - camera.getxPlane();
         double rayDirY0 = camera.getyDir() - camera.getyPlane();
         double rayDirX1 = camera.getxDir() + camera.getxPlane();
         double rayDirY1 = camera.getyDir() + camera.getyPlane();

         // Current y position compared to the center of the screen (the horizon)
         int p = y - screenHeight / 2;
         double posZ = 0.5 * screenHeight;

         double rowDistance = posZ / p;

         double floorStepX = rowDistance * (rayDirX1 - rayDirX0) / screenWidth;
         double floorStepY = rowDistance * (rayDirY1 - rayDirY0) / screenWidth;

         double floorX = camera.getxPos() + rowDistance * rayDirX0;
         double floorY = camera.getyPos() + rowDistance * rayDirY0;

         //int mapX = (int)camera.getxPos();
         //int mapY = (int)camera.getyPos();
         //int textNum = this.map[mapX][mapY] - 1;

         for(int x = 0; x < screenWidth; ++x)
         {
            // the cell coord is simply got from the integer parts of floorX and floorY
            int cellX = (int)(floorX);
            int cellY = (int)(floorY);

            // get the texture coordinate from the fractional part
            int tx = (int)(64 * (floorX - cellX)) & (64 - 1);
            int ty = (int)(64 * (floorY - cellY)) & (64 - 1);

            floorX += floorStepX;
            floorY += floorStepY;

            // choose texture and draw the pixel
            int floorTexture = 3;
            int ceilingTexture = 1;
            int color;

            // floor
            // ((Texture)this.textures.get(textNum)).pixels[texX + texY * ((Texture)this.textures.get(textNum)).SIZE];
            //color = texture[floorTexture][width * ty + tx];
            color = this.textures.get(floorTexture).pixels[tx + ty * 64];
            //color = (color >> 1) & 8355711; // make a bit darker
            for(x = 0; x < pixels.length / 2; ++x) {
               if (pixels[x] != color) {  // ceiling
                  pixels[x] = color;
               }
            }


            //pixels[y][x] = color;

            //ceiling (symmetrical, at screenHeight - y - 1 instead of y)
            color = ((Texture)this.textures.get(ceilingTexture)).pixels[64 * ty + tx];
            //color = (color >> 1) & 8355711; // make a bit darker
            for(x = pixels.length / 2; x < pixels.length; ++x) {
               if (pixels[x] != color) { // floor
                  pixels[x] = color;
                  //pixels[x] = 775024;
               }
            }
            //pixels[screenHeight - y - 1][x] = color;
         }
      }*/

      for(x = 0; x < this.width; ++x) {
         double cameraX = (double)(2 * x) / (double)this.width - 1.0D;
         double rayDirX = camera.getxDir() + camera.getxPlane() * cameraX;
         double rayDirY = camera.getyDir() + camera.getyPlane() * cameraX;
         int mapX = (int)camera.getxPos();
         int mapY = (int)camera.getyPos();
         double deltaDistX = Math.sqrt(1.0D + rayDirY * rayDirY / (rayDirX * rayDirX));
         double deltaDistY = Math.sqrt(1.0D + rayDirX * rayDirX / (rayDirY * rayDirY));
         boolean hit = false;
         boolean side = false;
         double sideDistX;
         byte stepX;
         if (rayDirX < 0.0D) {
            stepX = -1;
            sideDistX = (camera.getxPos() - (double)mapX) * deltaDistX;
         } else {
            stepX = 1;
            sideDistX = ((double)mapX + 1.0D - camera.getxPos()) * deltaDistX;
         }

         double sideDistY;
         byte stepY;
         if (rayDirY < 0.0D) {
            stepY = -1;
            sideDistY = (camera.getyPos() - (double)mapY) * deltaDistY;
         } else {
            stepY = 1;
            sideDistY = ((double)mapY + 1.0D - camera.getyPos()) * deltaDistY;
         }

         while(!hit) {
            if (sideDistX < sideDistY) {
               sideDistX += deltaDistX;
               mapX += stepX;
               side = false;
            } else {
               sideDistY += deltaDistY;
               mapY += stepY;
               side = true;
            }

            if (this.map[mapX][mapY] > 0) {
               hit = true;
            }
         }

         double perpWallDist;
         if (!side) {
            perpWallDist = Math.abs(((double)mapX - camera.getxPos() + (double)((1 - stepX) / 2)) / rayDirX);
         } else {
            perpWallDist = Math.abs(((double)mapY - camera.getyPos() + (double)((1 - stepY) / 2)) / rayDirY);
         }

         int lineHeight;
         if (perpWallDist > 0.0D) {
            lineHeight = Math.abs((int)((double)this.height / perpWallDist));
         } else {
            lineHeight = this.height;
         }

         int drawStart = -lineHeight / 2 + this.height / 2;
         if (drawStart < 0) {
            drawStart = 0;
         }

         int drawEnd = lineHeight / 2 + this.height / 2;
         if (drawEnd >= this.height) {
            drawEnd = this.height - 1;
         }

         int textNum = this.map[mapX][mapY] - 1;
         double wallX;
         if (side) {
            wallX = camera.getxPos() + ((double)mapY - camera.getyPos() + (double)((1 - stepY) / 2)) / rayDirY * rayDirX;
         } else {
            wallX = camera.getyPos() + ((double)mapX - camera.getxPos() + (double)((1 - stepX) / 2)) / rayDirX * rayDirY;
         }

         wallX -= Math.floor(wallX);
         int texX = (int)(wallX * (double)((Texture)this.textures.get(textNum)).SIZE);
         if (!side && rayDirX > 0.0D) {
            texX = ((Texture)this.textures.get(textNum)).SIZE - texX - 1;
         }

         if (side && rayDirY < 0.0D) {
            texX = ((Texture)this.textures.get(textNum)).SIZE - texX - 1;
         }

         for(int y = drawStart; y < drawEnd; ++y) {
            int texY = (y * 2 - this.height + lineHeight << 6) / lineHeight / 2;
            int color;
            if (!side) {
               color = ((Texture)this.textures.get(textNum)).pixels[texX + texY * ((Texture)this.textures.get(textNum)).SIZE];
            } else {
               color = ((Texture)this.textures.get(textNum)).pixels[texX + texY * ((Texture)this.textures.get(textNum)).SIZE] >> 1 & 8355711;
            }

            pixels[x + y * this.width] = color;
         }
      }

      return pixels;
   }
}
