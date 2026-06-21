package com.bsodsoftware.abraxas.engine.raycaster.maps;

import java.util.*;

public class MapGenerator {
    private Random random;
    private int width;
    private int height;
    private List<Room> rooms;

    public MapGenerator(int width, int height) {
        this.random = new Random();
        this.width = width;
        this.height = height;
        this.rooms = new ArrayList<>();
    }

    public int[][] generateFloor() {
        int[][] map = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = 2; // greystone
            }
        }

        for (int i = 0; i < 10; i++) {
            int w = 3 + random.nextInt(6);
            int h = 3 + random.nextInt(6);

            int x = 1 + random.nextInt(width - w - 2);
            int y = 1 + random.nextInt(height - h - 2);

            Room newRoom = new Room(x, y, w, h);

            boolean overlaps = false;

            for (Room other : rooms) {
                if (x < other.getX() + other.getWeight() &&
                        x + w > other.getX() &&
                        y < other.getY() + other.getHeight() &&
                        y + h > other.getY()) {
                    overlaps = true;
                    break;
                }
            }

            if (!overlaps) {
                rooms.add(newRoom);

                for (int dx = 0; dx < w; dx++) {
                    for (int dy = 0; dy < h; dy++) {
                        map[x + dx][y + dy] = 0;
                    }
                }
            }
        }

        for (int i = 1; i < rooms.size(); i++) {
            Room a = rooms.get(i - 1);
            Room b = rooms.get(i);

            int x1 = a.getCenterX();
            int y1 = a.getCenterY();

            int x2 = b.getCenterX();
            int y2 = b.getCenterY();

            if (random.nextBoolean()) {
                for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
                    map[x][y1] = 0;
                }

                for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                    map[x2][y] = 0;
                }
            } else {
                for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                    map[x1][y] = 0;
                }

                for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
                    map[x][y2] = 0;
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

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
