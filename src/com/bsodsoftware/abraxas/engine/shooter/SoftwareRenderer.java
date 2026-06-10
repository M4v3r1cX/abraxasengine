package com.bsodsoftware.abraxas.engine.shooter;

import com.bsodsoftware.abraxas.engine.graphics.Sprite;
import com.bsodsoftware.abraxas.engine.graphics.Texture;
import com.bsodsoftware.abraxas.engine.graphics.raycaster.LightSource;
import com.bsodsoftware.abraxas.engine.graphics.raycaster.SpriteRaycaster;

import java.awt.Color;
import java.util.List;

public class SoftwareRenderer {
   public int[][] map;
   public int mapWidth;
   public int mapHeight;
   public int width;
   public int height;
   public List<Texture> textures;
   public List<LightSource> lights;

   public SoftwareRenderer(int[][] map, List<Texture> textures, int mapWidth, int mapHeight, int width, int height,
                           List<LightSource> lights) {
      this.map = map;
      this.textures = textures;
      this.mapWidth = mapWidth;
      this.mapHeight = mapHeight;
      this.width = width;
      this.height = height;
      this.lights = lights;
   }

   public int[] update(Camera camera, int[] pixels, List<SpriteRaycaster> sprites) {
      double[] zBuffer = new double[this.width];
      int x;

      for(x = 0; x < pixels.length / 2; ++x) {
         if (pixels[x] != Color.DARK_GRAY.getRGB()) {
            pixels[x] = Color.DARK_GRAY.getRGB();
         }
      }

      // Ceiling/Floor casting
      for (int y = this.height / 2 + 1; y < this.height; y++) {
         double rayDirX0 = camera.getxDir() - camera.getxPlane();
         double rayDirY0 = camera.getyDir() - camera.getyPlane();
         double rayDirX1 = camera.getxDir() + camera.getxPlane();
         double rayDirY1 = camera.getyDir() + camera.getyPlane();

         int p = y - this.height / 2;
         double posZ = 0.5 * this.height;

         double rowDistance = posZ / p;

         double floorStepX = rowDistance * (rayDirX1 - rayDirX0) / this.width;
         double floorStepY = rowDistance * (rayDirY1 - rayDirY0) / this.width;

         double floorX = camera.getxPos() + rowDistance * rayDirX0;
         double floorY = camera.getyPos() + rowDistance * rayDirY0;

         for (int z = 0; z < this.width; ++z) {
            int cellX = (int)(floorX);
            int cellY = (int)(floorY);

            int tx = (int)(64 * (floorX - cellX)) & 63;
            int ty = (int)(64 * (floorY - cellY)) & 63;

            floorX += floorStepX;
            floorY += floorStepY;

            // Floor
            Texture floorTex = this.textures.get(5);
            int color = floorTex.getPixels()[tx + ty * floorTex.getSize()];

            color = applyLighting(color, floorX, floorY, rowDistance);
            pixels[z + y * this.width] = color;

            // Ceiling
            Texture ceilTex = this.textures.get(7);
            int ceilColor = ceilTex.getPixels()[tx + ty * ceilTex.getSize()];
            ceilColor = shadeColor(ceilColor, rowDistance);

            pixels[z + (this.height - y) * this.width] = ceilColor;
         }
      }

      // Wall casting
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
         zBuffer[x] = perpWallDist;

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
         int textureSize = this.textures.get(textNum).getSize();
         double wallX;
         if (side) {
            wallX = camera.getxPos() + ((double)mapY - camera.getyPos() + (double)((1 - stepY) / 2)) / rayDirY * rayDirX;
         } else {
            wallX = camera.getyPos() + ((double)mapX - camera.getxPos() + (double)((1 - stepX) / 2)) / rayDirX * rayDirY;
         }
         double worldX = camera.getxPos() + rayDirX * perpWallDist;
         double worldY = camera.getyPos() + rayDirY * perpWallDist;

         wallX -= Math.floor(wallX);
         int texX = (int)(wallX * (double)textureSize);
         if (!side && rayDirX > 0.0D) {
            texX = textureSize - texX - 1;
         }

         if (side && rayDirY < 0.0D) {
            texX = textureSize - texX - 1;
         }

         for(int y = drawStart; y < drawEnd; ++y) {
            int texY = (y * 2 - this.height + lineHeight << 6) / lineHeight / 2;
            int color;
            if (!side) {
               color = this.textures.get(textNum).getPixels()[texX + texY * textureSize];
            } else {
               color = this.textures.get(textNum).getPixels()[texX + texY * textureSize] >> 1 & 8355711;
            }

            //color = shadeColor(color, perpWallDist);
            color = applyLighting(color, worldX, worldY, perpWallDist);
            pixels[x + y * this.width] = color;
         }
      }

      // Sprite Casting
      for (SpriteRaycaster sprite : sprites) {
         double worldX = sprite.getX();
         double worldY = sprite.getY();
         double spriteX = sprite.getX() - camera.getxPos();
         double spriteY = sprite.getY() - camera.getyPos();

         double invDet = 1.0 / (camera.getxPlane() * camera.getyDir() - camera.getxDir() * camera.getyPlane());

         double transformX = invDet * (camera.getyDir() * spriteX - camera.getxDir() * spriteY);
         double transformY = invDet * (-camera.getyPlane() * spriteX + camera.getxPlane() * spriteY);

         int spriteScreenX = (int)(((double) this.width / 2) * (1 + transformX / transformY));

         int spriteHeight = Math.abs((int)(this.height / transformY));
         int drawStartY = -spriteHeight / 2 + this.height / 2;
         if (drawStartY < 0) drawStartY = 0;

         int drawEndY = spriteHeight / 2 + this.height / 2;
         if (drawEndY >= this.height) drawEndY = this.height - 1;

         int spriteWidth = Math.abs((int)(this.height / transformY));
         int drawStartX = -spriteWidth / 2 + spriteScreenX;
         if (drawStartX < 0) drawStartX = 0;

         int drawEndX = spriteWidth / 2 + spriteScreenX;
         if (drawEndX >= this.width) drawEndX = this.width - 1;

         Texture texture = this.textures.get(sprite.getTexture() - 1);

         for (int stripe = drawStartX; stripe < drawEndX; stripe++) {

            int texX = (256 * (stripe - (-spriteWidth / 2 + spriteScreenX)) * texture.getSize() / spriteWidth) / 256;

            if (transformY > 0 && stripe < this.width && transformY < zBuffer[stripe]) {
               for (int y = drawStartY; y < drawEndY; y++) {
                  int d = (y) * 256 - this.height * 128 + spriteHeight * 128;
                  int texY = ((d * texture.getSize()) / spriteHeight) / 256;
                  int color = texture.getPixels()[texX + texY * texture.getSize()];
                  if ((color & 0x00FFFFFF) != 0) {
                     color = applyLighting(color, worldX, worldY, transformY);
                     pixels[stripe + y * this.width] = color;
                  }
               }
            }
         }
      }

      return pixels;
   }

   private int applyLighting(int color, double worldX, double worldY, double distance) {
      double brightness = 0.2;

      for (LightSource light : lights) {
         double dx = worldX - light.getX();
         double dy = worldY - light.getY();
         double dist = Math.sqrt(dx * dx + dy * dy);
         double attenuation = light.getIntensity() / (1.0 + dist * dist * 0.5);
         double falloff = Math.max(0.0, 1.0 - (dist / light.getRadius()));

         brightness += attenuation * falloff;

      }

      brightness *= 1.0 / (1.0 + distance * 0.1);
      brightness = Math.min(brightness, 1.0);

      int r = (int)(((color >> 16) & 0xFF) * brightness);
      int g = (int)(((color >> 8) & 0xFF) * brightness);
      int b = (int)((color & 0xFF) * brightness);

      return (r << 16) | (g << 8) | b;
   }

   private int shadeColor(int color, double distance) {
      double strength = 0.15;
      double shade = 1.0 / (1.0 + distance * strength);
      int r = (int)(((color >> 16) & 0xFF) * shade);
      int g = (int)(((color >> 8) & 0xFF) * shade);
      int b = (int)((color & 0xFF) * shade);

      return (r << 16) | (g << 8) | b;
   }
}
