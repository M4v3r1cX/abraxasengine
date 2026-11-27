package com.bsodsoftware.abraxas.engine.graphics.lights;

import java.util.List;
import java.util.Random;

public class LightEngine implements Runnable {
    float[][] lightMapR;
    float[][] lightMapG;
    float[][] lightMapB;
    int[][] map;
    int mapWidth;
    int mapHeight;
    List<LightSource> lights;
    Random random;

    public volatile boolean running = true;

    public LightEngine(int[][] map, int mapWidth, int mapHeight, List<LightSource> lights, float[][] lightMapR, float[][] lightMapG, float[][] lightMapB) {
        this.map = map;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.lights = lights;
        this.lightMapR = lightMapR;
        this.lightMapG = lightMapG;
        this.lightMapB = lightMapB;
        this.random = new Random();
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
        if (lightMapR == null || lightMapB == null || lightMapG == null) return;

        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                lightMapR[x][y] = 0.08f;
                lightMapG[x][y] = 0.08f;
                lightMapB[x][y] = 0.10f;
                for (LightSource light : lights) {
                    float dx = x + 0.5f - light.getX();
                    float dy = y + 0.5f - light.getY();

                    float distSq = dx * dx + dy * dy;
                    float radiusSq = light.getRadius() * light.getRadius();

                    if (distSq > radiusSq) continue;

                    float intensity = light.getIntensity() * light.getFlickerValue();
                    float atten = intensity / (1.0f + distSq * 0.2f);
                    float falloff = 1.0f - (distSq / radiusSq);
                    falloff = Math.max(0, falloff);
                    falloff = falloff * falloff * (3 - 2 * falloff);

                    lightMapR[x][y] += light.getRed() * atten * falloff;
                    lightMapG[x][y] += light.getGreen() * atten * falloff;
                    lightMapB[x][y] += light.getBlue() * atten * falloff;
                }
                lightMapR[x][y] = Math.min(lightMapR[x][y], 1.0f);
                lightMapG[x][y] = Math.min(lightMapG[x][y], 1.0f);
                lightMapB[x][y] = Math.min(lightMapB[x][y], 1.0f);
            }
        }
    }


    private void updateTorch(LightSource light) {
        if (Math.abs(light.getFlickerValue() - light.getFlickerTarget()) < 0.02f) {
            light.setFlickerTarget(0.8f + random.nextFloat() * 0.4f);
        }
        float value = light.getFlickerValue();
        value += (light.getFlickerTarget() - value) * light.getFlickerSpeed();
        light.setFlickerValue(value);
    }

}
