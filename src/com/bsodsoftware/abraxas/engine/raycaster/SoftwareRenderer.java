package com.bsodsoftware.abraxas.engine.raycaster;

import com.bsodsoftware.abraxas.engine.graphics.Texture;
import com.bsodsoftware.abraxas.engine.graphics.raycaster.LightSource;
import com.bsodsoftware.abraxas.engine.graphics.raycaster.SpriteRaycaster;
import com.bsodsoftware.abraxas.engine.things.Door;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class SoftwareRenderer {
   public int[][] map;
   public int mapWidth;
   public int mapHeight;
   public int width;
   public int height;
   public List<Texture> textures;
   public List<LightSource> lights;
   private List<LightSource> activeLights;
   private float[] zBuffer;

   public SoftwareRenderer(int[][] map, List<Texture> textures, int mapWidth, int mapHeight, int width, int height,
                           List<LightSource> lights) {
      this.map = map;
      this.textures = textures;
      this.mapWidth = mapWidth;
      this.mapHeight = mapHeight;
      this.width = width;
      this.height = height;
      this.lights = lights;
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
      this.activeLights = new ArrayList<>();

      for (LightSource light : lights) {
         float dx = camX - light.getX();
         float dy = camY - light.getY();
         float distSq = dx*dx + dy*dy;
         float maxRange = light.getRadius() + 10;
         if (distSq < maxRange * maxRange) {
            activeLights.add(light);
         }
      }


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
            int cellX = (int)(floorX);
            int cellY = (int)(floorY);

            float fracX = floorX - cellX;
            float fracY = floorY - cellY;

            int tx = (int)(fracX * 64) & 63;
            int ty = (int)(fracY * 64) & 63;

            floorX += floorStepX;
            floorY += floorStepY;

            // Floor
            int color = floorTexPixels[tx + ty * floorTexSize];

            color = applyLighting(color, floorX, floorY, rowDistance);
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

         float perpWallDist;
         if (!side) {
            perpWallDist = Math.abs(((float)mapX - camX + (float)((1 - stepX) / 2)) / rayDirX);
         } else {
            perpWallDist = Math.abs(((float)mapY - camY + (float)((1 - stepY) / 2)) / rayDirY);
         }
         zBuffer[x] = perpWallDist;

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

            if (this.map[mapX][mapY] == 1) {    // Muralla
               hit = true;
            } else if(map[mapX][mapY] == 2) {   // Puerta
               if (doors != null && doors.length > 0) {
                  Door door = doors[mapX][mapY];
                  if (door != null) {
                     float hitPos;
                     if (!side) {
                        hitPos = camY + perpWallDist * rayDirY;
                     } else {
                        hitPos = camX + perpWallDist * rayDirX;
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

         int tile = this.map[mapX][mapY];
         if (tile <= 0) {
            tile = 1;
         }
         int textNum = Math.min(tile - 1, textures.size() - 1);
         Texture texture = this.textures.get(textNum);

         int textureSize = texture.getSize();
         int[] texturePixels = texture.getPixels();
         float wallX;
         if (side) {
            wallX = camX + ((float)mapY - camY + (float)((1 - stepY) / 2)) / rayDirY * rayDirX;
         } else {
            wallX = camY + ((float)mapX - camX + (float)((1 - stepX) / 2)) / rayDirX * rayDirY;
         }
         float worldX = camX + rayDirX * perpWallDist;
         float worldY = camY + rayDirY * perpWallDist;

         wallX -= (float) Math.floor(wallX);
         int texX = 0;
         if (this.map[mapX][mapY] == 1) {    // Muralla
            texX = (int)(wallX * textureSize);
         } else if(map[mapX][mapY] == 2) {   // Puerta
            Door door = doors[mapX][mapY];
            float shifted = wallX - door.openAmount;
            texX = (int)(shifted * textureSize);
            if (texX < 0) texX = 0;
            if (texX >= textureSize) texX = textureSize - 1;
         }

         if (!side && rayDirX > 0.0D) {
            texX = textureSize - texX - 1;
         }

         if (side && rayDirY < 0.0D) {
            texX = textureSize - texX - 1;
         }
         float invLineHeight = 1.0f / lineHeight;
         float brightnessFactor = computeLighting(worldX, worldY, perpWallDist);

         for(int y = drawStart; y < drawEnd; ++y) {
            int texY = (int)((y * 2 - height + lineHeight) * 32 * invLineHeight);
            int color;
            if (!side) {
               color = texturePixels[texX + texY * textureSize];
            } else {
               color = texturePixels[texX + texY * textureSize] >> 1 & 8355711;
            }

            color = applyBrightness(color,brightnessFactor);
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

         float brightness = computeLighting(worldX, worldY, transformY);

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
                     color = applyBrightness(color, brightness);
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

   private int applyLighting(int color, float worldX, float worldY, float distance) {
      float brightness = 0.2f;

      for (LightSource light : activeLights) {
         float dx = worldX - light.getX();
         float dy = worldY - light.getY();

         float distSq = dx * dx + dy * dy;
         float radiusSq = light.getRadius() * light.getRadius();

         if (distSq > radiusSq) continue;

         float attenuation = light.getIntensity() / (1.0f + distSq * 0.2f);
         float falloff = Math.max(0.0f, 1.0f - (distSq / radiusSq));

         brightness += attenuation * falloff;

         /*if (distance > 20) {
            brightness *= 0.2f;
         }
         else {
            float dx = worldX - light.getX();
            float dy = worldY - light.getY();

            float distSq = dx * dx + dy * dy;
            float attenuation = light.getIntensity() / (1.0f + distSq * 0.2f);
            float falloff = (float) Math.max(0.0, 1.0 - (distSq / light.getRadius()));

            brightness += attenuation * falloff;
         }*/
      }

      brightness *= 1.0f / (1.0f + distance * 0.1f);
      brightness = (float) Math.min(brightness, 1.0);

      int r = (int)(((color >> 16) & 0xFF) * brightness);
      int g = (int)(((color >> 8) & 0xFF) * brightness);
      int b = (int)((color & 0xFF) * brightness);

      return (r << 16) | (g << 8) | b;
   }

   private int shadeColor(int color, float distance) {
      float strength = 0.15f;
      float shade = 1.0f/ (1.0f + distance * strength);
      int r = (int)(((color >> 16) & 0xFF) * shade);
      int g = (int)(((color >> 8) & 0xFF) * shade);
      int b = (int)((color & 0xFF) * shade);

      return (r << 16) | (g << 8) | b;
   }


   private float computeLighting(float worldX, float worldY, float distance) {
      float brightness = 0.01f;
      for (LightSource light : lights) {
         if (distance > 20) {
            brightness *= 0.01f;
         } else {
            float dx = worldX - light.getX();
            float dy = worldY - light.getY();
            float distSq = dx * dx + dy * dy;
            float attenuation = light.getIntensity() / (1.0f + distSq * 0.2f);
            float radiusSq = light.getRadius() * light.getRadius();
            float falloff = Math.max(0.0f, 1.0f - (distSq / radiusSq));

            brightness += attenuation * falloff;
         }
      }

      brightness *= 1.0f / (1.0f + distance * 0.1f);

      return Math.min(brightness, 1.0f);
   }


   private int applyBrightness(int color, float brightness) {
      int r = (int)(((color >> 16) & 0xFF) * brightness);
      int g = (int)(((color >> 8) & 0xFF) * brightness);
      int b = (int)((color & 0xFF) * brightness);

      return (r << 16) | (g << 8) | b;
   }
}
