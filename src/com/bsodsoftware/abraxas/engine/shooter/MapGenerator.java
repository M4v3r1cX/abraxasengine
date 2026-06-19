package com.bsodsoftware.abraxas.engine.shooter;

import java.util.*;

public class MapGenerator {
    Random random;
    int width;
    int height;

    public MapGenerator(int width, int height) {
        this.random = new Random();
        this.width = width;
        this.height = height;
    }

    public int[][] generateFloor() {
        int map[][] = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = 2;
            }
        }

        // No tengo idea que es esta wea
        int[][] directions = {
                { 2, 0},
                {-2, 0},
                { 0, 2},
                { 0,-2}
        };

        carve(1,1,map);

        for (int i = 0; i < 5; i++) {
            int rx = 2 + random.nextInt(width - 4);
            int ry = 2 + random.nextInt(height - 4);

            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    map[rx + dx][ry + dy] = 0;
                }
            }
        }


        for (int x = 0; x < width; x++) {
            map[x][0] = 2;
            map[x][height - 1] = 2;
        }

        for (int y = 0; y < height; y++) {
            map[0][y] = 2;
            map[width - 1][y] = 2;
        }


        map[1][0] = 1; // entrance
        map[width - 2][height - 1] = 1; // exit

        return map;
    }


    private void carve(int x, int y, int[][] map) {
        map[x][y] = 0;

        List<int[]> dirs = new ArrayList<>(Arrays.asList(
                new int[]{2, 0},
                new int[]{-2, 0},
                new int[]{0, 2},
                new int[]{0, -2}
        ));

        Collections.shuffle(dirs);

        for (int[] dir : dirs) {
            int nx = x + dir[0];
            int ny = y + dir[1];

            if (nx > 0 && ny > 0 && nx < map.length - 1 && ny < map[0].length - 1) {
                if (map[nx][ny] == 2) {

                    // remove wall between
                    map[x + dir[0] / 2][y + dir[1] / 2] = 0;

                    carve(nx, ny, map);
                }
            }
        }
    }

}
