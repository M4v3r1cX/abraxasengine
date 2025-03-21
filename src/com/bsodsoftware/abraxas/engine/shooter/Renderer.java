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
         if (pixels[x] != Color.DARK_GRAY.getRGB()) {
            pixels[x] = Color.DARK_GRAY.getRGB();
         }
      }

      for(x = pixels.length / 2; x < pixels.length; ++x) {
         if (pixels[x] != Color.gray.getRGB()) {
            pixels[x] = Color.gray.getRGB();
            //pixels[x] = 775024;
         }
      }

      for(x = 0; x < this.width; ++x) {
         double cameraX = (double)(2 * x) / (double)this.width - 1.0D;
         double rayDirX = camera.xDir + camera.xPlane * cameraX;
         double rayDirY = camera.yDir + camera.yPlane * cameraX;
         int mapX = (int)camera.xPos;
         int mapY = (int)camera.yPos;
         double deltaDistX = Math.sqrt(1.0D + rayDirY * rayDirY / (rayDirX * rayDirX));
         double deltaDistY = Math.sqrt(1.0D + rayDirX * rayDirX / (rayDirY * rayDirY));
         boolean hit = false;
         boolean side = false;
         double sideDistX;
         byte stepX;
         if (rayDirX < 0.0D) {
            stepX = -1;
            sideDistX = (camera.xPos - (double)mapX) * deltaDistX;
         } else {
            stepX = 1;
            sideDistX = ((double)mapX + 1.0D - camera.xPos) * deltaDistX;
         }

         double sideDistY;
         byte stepY;
         if (rayDirY < 0.0D) {
            stepY = -1;
            sideDistY = (camera.yPos - (double)mapY) * deltaDistY;
         } else {
            stepY = 1;
            sideDistY = ((double)mapY + 1.0D - camera.yPos) * deltaDistY;
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
            perpWallDist = Math.abs(((double)mapX - camera.xPos + (double)((1 - stepX) / 2)) / rayDirX);
         } else {
            perpWallDist = Math.abs(((double)mapY - camera.yPos + (double)((1 - stepY) / 2)) / rayDirY);
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
            wallX = camera.xPos + ((double)mapY - camera.yPos + (double)((1 - stepY) / 2)) / rayDirY * rayDirX;
         } else {
            wallX = camera.yPos + ((double)mapX - camera.xPos + (double)((1 - stepX) / 2)) / rayDirX * rayDirY;
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
