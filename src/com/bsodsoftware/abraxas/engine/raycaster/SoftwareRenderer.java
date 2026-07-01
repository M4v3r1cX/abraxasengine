package com.bsodsoftware.abraxas.engine.raycaster;

import com.bsodsoftware.abraxas.engine.graphics.Texture;
import com.bsodsoftware.abraxas.engine.graphics.raycaster.LightSource;
import com.bsodsoftware.abraxas.engine.graphics.raycaster.SpriteRaycaster;
import com.bsodsoftware.abraxas.engine.things.Door;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class SoftwareRenderer {
   public static final int TILE_FLOOR = 0;
   public static final int TILE_WALL = 2;
   public static final int TILE_DOOR = 3;
   public int[][] map;
   public int mapWidth;
   public int mapHeight;
   public int width;
   public int height;
   public List<Texture> textures;
   private float[][] lightMapR;
   private float[][] lightMapG;
   private float[][] lightMapB;
   private float[] zBuffer;

   public SoftwareRenderer(int[][] map, List<Texture> textures, int mapWidth, int mapHeight, int width, int height,
                           float[][] lightMapR, float[][] lightMapG, float[][] lightMapB) {
      this.map = map;
      this.textures = textures;
      this.mapWidth = mapWidth;
      this.mapHeight = mapHeight;
      this.width = width;
      this.height = height;
      this.lightMapR = lightMapR;
      this.lightMapG = lightMapG;
      this.lightMapB = lightMapB;
      this.zBuffer = new float[this.width];
   }

   public int[] update(Camera camera, int[] pixels, List<SpriteRaycaster> sprites, Door[][] doors) {
      int x;
      float camX = camera.getxPos();
      float camY = camera.getyPos();
      float dirX = camera.getxDir();
      float dirY = camera.getyDir();
      float planeX = camera.getxPlane();
      float planeY = camera.getyPlane();

      // Ceiling/Floor casting
      Texture floorTex = this.textures.get(5);
      Texture ceilTex = this.textures.get(7);
      int[] floorTexPixels = floorTex.getPixels();
      int floorTexSize = floorTex.getSize();
      int[] ceilTexPixels = ceilTex.getPixels();
      int ceilTexSize = ceilTex.getSize();

      for (int y = this.height / 2 + 1; y < this.height; y+= 2) {
         float rayDirX0 = dirX - planeX;
         float rayDirY0 = dirY - planeY;
         float rayDirX1 = dirX + planeX;
         float rayDirY1 = dirY + planeY;

         int p = y - this.height / 2;
         float posZ = 0.5f * this.height;

         float rowDistance = posZ / p;

         float floorStepX = rowDistance * (rayDirX1 - rayDirX0) / this.width;
         float floorStepY = rowDistance * (rayDirY1 - rayDirY0) / this.width;

         float floorX = camX + rowDistance * rayDirX0;
         float floorY = camY + rowDistance * rayDirY0;

         for (int z = 0; z < this.width; ++z) {
            int cellX = (int)floorX;
            int cellY = (int)floorY;

            float fracX = floorX - cellX;
            float fracY = floorY - cellY;

            int tx = (int)(fracX * 64) & 63;
            int ty = (int)(fracY * 64) & 63;

            floorX += floorStepX;
            floorY += floorStepY;

            // Floor
            int color = floorTexPixels[tx + ty * floorTexSize];

            float cellX2 = floorX;
            float cellY2 = floorY;

            if (cellX2 < 0) cellX2 = 0;
            if (cellY2 < 0) cellY2 = 0;
            if (cellX2 >= mapWidth)  cellX2 = mapWidth - 1;
            if (cellY2 >= mapHeight) cellY2 = mapHeight - 1;

            float r = sampleLight(lightMapR, cellX2, cellY2);
            float g = sampleLight(lightMapG, cellX2, cellY2);
            float b = sampleLight(lightMapB, cellX2, cellY2);

            color = applyColoredLighting(color, r, g, b);
            pixels[z + y * this.width] = color;
            if (y + 1 < this.height) {
               pixels[z + (y + 1) * this.width] = color;
            }

            // Ceiling
            int ceilColor = ceilTexPixels[tx + ty * ceilTexSize];
            ceilColor = shadeColor(ceilColor, rowDistance);
            int ceilY = this.height - y;
            pixels[z + ceilY * this.width] = ceilColor;
            if (ceilY - 1 >= 0) {
               pixels[z + (ceilY - 1) * this.width] = ceilColor;
            }
         }
      }

      // Wall casting
      for(x = 0; x < this.width; ++x) {
         float cameraX = (2f * x) / this.width - 1.0f;
         float rayDirX = dirX + planeX * cameraX;
         float rayDirY = dirY + planeY * cameraX;
         float deltaDistX = (float) Math.sqrt(1.0D + rayDirY * rayDirY / (rayDirX * rayDirX));
         float deltaDistY = (float) Math.sqrt(1.0D + rayDirX * rayDirX / (rayDirY * rayDirY));
         boolean hit = false;
         boolean side = false;
         float sideDistX;
         byte stepX;

         int mapX = (int)camX;
         int mapY = (int)camY;

         if (rayDirX < 0.0D) {
            stepX = -1;
            sideDistX = (camX - (float)mapX) * deltaDistX;
         } else {
            stepX = 1;
            sideDistX = ((float)mapX + 1.0f - camX) * deltaDistX;
         }

         float sideDistY;
         byte stepY;
         if (rayDirY < 0.0D) {
            stepY = -1;
            sideDistY = (camY - (float)mapY) * deltaDistY;
         } else {
            stepY = 1;
            sideDistY = ((float)mapY + 1.0f - camY) * deltaDistY;
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

            if (mapX < 0 || mapY < 0 || mapX >= mapWidth || mapY >= mapHeight) {
               hit = true;
               break;
            }

            if (this.map[mapX][mapY] == TILE_WALL) {
               hit = true;
            } else if(map[mapX][mapY] == TILE_DOOR) {
               if (doors != null && doors.length > 0) {
                  Door door = doors[mapX][mapY];
                  if (door != null) {
                     float dist;
                     if (!side) {
                        dist = sideDistX - deltaDistX;
                     } else {
                        dist = sideDistY - deltaDistY;
                     }
                     float hitPos;

                     if (!side) {
                        hitPos = camY + dist * rayDirY;
                     } else {
                        hitPos = camX + dist * rayDirX;
                     }
                     hitPos -= (int)hitPos;
                     if (hitPos > door.openAmount) {
                        hit = true;
                     } else {
                        continue;
                     }
                  } else {
                     continue;
                  }
               } else {
                  continue;
               }
            }
         }

         float perpWallDist;
         if (!side) {
            perpWallDist = Math.abs(((float)mapX - camX + (float)((1 - stepX) / 2)) / rayDirX);
         } else {
            perpWallDist = Math.abs(((float)mapY - camY + (float)((1 - stepY) / 2)) / rayDirY);
         }
         if (perpWallDist < 0.0001f) perpWallDist = 0.0001f;
         zBuffer[x] = perpWallDist;

         if (mapX < 0) mapX = 0;
         if (mapY < 0) mapY = 0;
         if (mapX >= mapWidth)  mapX = mapWidth - 1;
         if (mapY >= mapHeight) mapY = mapHeight - 1;

         int lineHeight;
         if (perpWallDist > 0.0D) {
            lineHeight = Math.abs((int)((float)this.height / perpWallDist));
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
         int textNum = getTextureIndex(mapX, mapY);
         Texture texture = this.textures.get(textNum);

         int textureSize = texture.getSize();
         int[] texturePixels = texture.getPixels();
         float wallX;
         if (!side) {
            wallX = camY + perpWallDist * rayDirY;
         } else {
            wallX = camX + perpWallDist * rayDirX;
         }

         wallX -= (float) Math.floor(wallX);
         float worldX = camX + rayDirX * perpWallDist;
         float worldY = camY + rayDirY * perpWallDist;

         int texX = 0;
         if (this.map[mapX][mapY] == TILE_WALL) {
            texX = (int)(wallX * textureSize);
         } else if(map[mapX][mapY] == TILE_DOOR) {
            if (doors != null && doors.length > 0) {
               Door door = doors[mapX][mapY];
               if (door != null) {
                  float shifted = wallX - door.openAmount;
                  shifted = Math.max(0.0f, Math.min(1.0f, shifted));
                  texX = (int)(shifted * textureSize);
               } else {
                  texX = (int)(wallX * textureSize); // fallback
               }
            }
         }

         if (texX < 0) texX = 0;
         if (texX >= textureSize) texX = textureSize - 1;

         if (!side && rayDirX > 0.0D) {
            texX = textureSize - texX - 1;
         }

         if (side && rayDirY < 0.0D) {
            texX = textureSize - texX - 1;
         }

         float cellX = worldX;
         float cellY = worldY;

         if (cellX < 0) cellX = 0;
         if (cellY < 0) cellY = 0;
         if (cellX >= mapWidth)  cellX = mapWidth - 1;
         if (cellY >= mapHeight) cellY = mapHeight - 1;

         float lightR = sampleLight(lightMapR, worldX, worldY);
         float lightG = sampleLight(lightMapG, worldX, worldY);
         float lightB = sampleLight(lightMapB, worldX, worldY);
         for(int y = drawStart; y < drawEnd; ++y) {
            int d = y * 256 - height * 128 + lineHeight * 128;
            int texY = (d * textureSize) / lineHeight / 256;

            if (texY < 0) texY = 0;
            if (texY >= textureSize) texY = textureSize - 1;

            int color;
            if (!side) {
               color = texturePixels[texX + texY * textureSize];
            } else {
               color = texturePixels[texX + texY * textureSize] >> 1 & 8355711;
            }
            color = applyColoredLighting(color, lightR, lightG, lightB);

            pixels[x + y * this.width] = color;
         }
      }

      // Sprite Casting
      for (SpriteRaycaster sprite : sprites) {
         float spriteX = (float) (sprite.getX() - camX);
         float spriteY = (float) (sprite.getY() - camY);

         float invDet = 1.0f / (planeX * dirY - dirX * planeY);

         float transformX = invDet * (dirY * spriteX - dirX * spriteY);
         float transformY = invDet * (-planeY * spriteX + planeX * spriteY);

         if (transformY <= 0) continue;
         if (transformY > 20) continue;

         float worldX = (float) sprite.getX();
         float worldY = (float) sprite.getY();
         if (worldX < 0) worldX = 0;
         if (worldY < 0) worldY = 0;
         if (worldX >= mapWidth)  worldX = mapWidth - 1;
         if (worldY >= mapHeight) worldY = mapHeight - 1;

         float lightR = sampleLight(lightMapR, worldX, worldY);
         float lightG = sampleLight(lightMapG, worldX, worldY);
         float lightB = sampleLight(lightMapB, worldX, worldY);

         int spriteScreenX = (int)(((float) this.width / 2) * (1 + transformX / transformY));

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

         if (drawStartX >= width || drawEndX < 0) continue;
         for (int stripe = drawStartX; stripe < drawEndX; stripe += 2) {
            int texX = (256 * (stripe - (-spriteWidth / 2 + spriteScreenX)) * texture.getSize() / spriteWidth) / 256;

            if (transformY > 0 && stripe < this.width && transformY < zBuffer[stripe]) {
               for (int y = drawStartY; y < drawEndY; y++) {
                  int d = (y) * 256 - this.height * 128 + spriteHeight * 128;
                  int texY = ((d * texture.getSize()) / spriteHeight) / 256;
                  int color = texture.getPixels()[texX + texY * texture.getSize()];
                  if ((color & 0x00FFFFFF) != 0) {
                     color = applyColoredLighting(color, lightR, lightG, lightB);

                     pixels[stripe + y * this.width] = color;

                     if (stripe + 1 < width) {
                        pixels[stripe + 1 + y * width] = color;
                     }
                  }
               }
            }
         }
      }

      return pixels;
   }

   private int shadeColor(int color, float distance) {
      float strength = 0.15f;
      float shade = 1.0f/ (1.0f + distance * strength);
      int r = (int)(((color >> 16) & 0xFF) * shade);
      int g = (int)(((color >> 8) & 0xFF) * shade);
      int b = (int)((color & 0xFF) * shade);

      return (r << 16) | (g << 8) | b;
   }

   private int applyColoredLighting(int color, float rLight, float gLight, float bLight) {
      int r = (color >> 16) & 0xFF;
      int g = (color >> 8) & 0xFF;
      int b = color & 0xFF;

      r = Math.min(255, (int)(r * rLight));
      g = Math.min(255, (int)(g * gLight));
      b = Math.min(255, (int)(b * bLight));

      return (r << 16) | (g << 8) | b;
   }

   private int getTextureIndex(int mapX, int mapY) {
      if (mapX < 0 || mapY < 0 || mapX >= mapWidth || mapY >= mapHeight) {
         return 0;
      }
      int tile = map[mapX][mapY];
      if (tile <= 0) return 0;
      if (tile - 1 >= textures.size()) return 0;
      return tile - 1;
   }

   private float sampleLight(float[][] map, float worldX, float worldY) {
      int x0 = (int)worldX;
      int y0 = (int)worldY;

      float fx = worldX - x0;
      float fy = worldY - y0;

      int x1 = Math.min(x0 + 1, mapWidth - 1);
      int y1 = Math.min(y0 + 1, mapHeight - 1);

      x0 = Math.max(0, Math.min(x0, mapWidth - 1));
      y0 = Math.max(0, Math.min(y0, mapHeight - 1));

      float l00 = map[x0][y0];
      float l10 = map[x1][y0];
      float l01 = map[x0][y1];
      float l11 = map[x1][y1];

      float lx0 = l00 + fx * (l10 - l00);
      float lx1 = l01 + fx * (l11 - l01);

      return lx0 + fy * (lx1 - lx0);
   }
}
