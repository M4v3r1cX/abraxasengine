package com.bsodsoftware.abraxas.engine.graphics.raycaster;

import java.util.ArrayList;
import java.util.List;

public class LightEngine implements Runnable {
    float[][] lightMap;
    int[][] map;
    int mapWidth;
    int mapHeight;
    List<LightSource> lights;

    public volatile boolean running = true;

    public LightEngine(int[][] map, int mapWidth, int mapHeight, List<LightSource> lights, float[][] lightMap) {
        this.map = map;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.lights = lights;
        this.lightMap = lightMap;
    }

    @Override
    public void run() {
        while (running) {
            computeLightMap();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void computeLightMap() {
        if (lightMap == null) return;

        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                float brightness = 0.1f; // ambient
                for (LightSource light : lights) {
                    float dx = x + 0.5f - light.getX();
                    float dy = y + 0.5f - light.getY();

                    float distSq = dx * dx + dy * dy;
                    float radiusSq = light.getRadius() * light.getRadius();

                    if (distSq > radiusSq) continue;

                    float atten = light.getIntensity() / (1.0f + distSq * 0.2f);

                    float falloff = 1.0f - (distSq / radiusSq);
                    falloff = Math.max(0, falloff);
                    falloff = falloff * falloff * (3 - 2 * falloff);


                    brightness += atten * falloff;
                }
                lightMap[x][y] = Math.min(brightness, 1.0f);
            }
        }
    }
}
