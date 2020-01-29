package com.bsodsoftware.abraxas.engine.graphics;

import com.bsodsoftware.abraxas.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileMap {
    // posicion
    private double x,y;
    // bounds
    private int xmin,xmax,ymin,ymax;
    // what
    private double tween;
    // map
    private int[][] map;
    private int tileSize, numRows,numCols,width,height;
    // tileset
    private BufferedImage tileset;
    private int numTilesAcross;
    private Tile[][] tiles;
    // rendering
    private int rowOffset,colOffset,numRowsToDraw,numColsToDraw;

    public TileMap(int tileSize) {
        this.tileSize = tileSize;
        numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
        numColsToDraw = GamePanel.WIDTH / tileSize + 2;
        tween = 0.07;   // El qliao del tutorial dice que esta wea no funciona y nisiquiera se que es
    }

    public int getX() {
        return (int)x;
    }
    public int getY() {
        return (int)y;
    }
    public int getXmin() {
        return xmin;
    }
    public int getXmax() {
        return xmax;
    }
    public int getYmin() {
        return ymin;
    }
    public int getYmax() {
        return ymax;
    }
    public double getTween() {
        return tween;
    }
    public int[][] getMap() {
        return map;
    }
    public int getTileSize() {
        return tileSize;
    }
    public int getNumRows() {
        return numRows;
    }
    public int getNumCols() {
        return numCols;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public BufferedImage getTileset() {
        return tileset;
    }
    public int getNumTilesAcross() {
        return numTilesAcross;
    }
    public Tile[][] getTiles() {
        return tiles;
    }
    public int getRowOffset() {
        return rowOffset;
    }
    public int getColOffset() {
        return colOffset;
    }
    public int getNumRowsToDraw() {
        return numRowsToDraw;
    }
    public int getNumColsToDraw() {
        return numColsToDraw;
    }

    public int getType(int row, int col) {
        int rc = map[row][col];
        int r = rc / numTilesAcross;
        int c = rc % numTilesAcross;
        return tiles[r][c].getType();
    }

    public void setPosition(double x, double y) {
        this.x += (x - this.x) * tween;
        this.y += (y - this.y) * tween;

        fixBounds();
    }

    private void fixBounds(){
        if (x < xmin) x = xmin;
        if (x > xmax) x = xmax;
        if (y < ymin) y = ymin;
        if (y > ymax) y = ymax;
    }

    // 20:24

    public void loadTiles(String resource) {
        try {
            tileset = ImageIO.read(getClass().getResourceAsStream(resource));
            numTilesAcross = tileset.getWidth() / tileSize;
            tiles = new Tile[2][numTilesAcross];

            BufferedImage subImage;
            for (int col = 0; col < numTilesAcross; col++) {
                subImage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);
                tiles[0][col] = new Tile(subImage, Tile.NORMAL);
                subImage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
                tiles[1][col] = new Tile(subImage, Tile.BLOCKED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loadMap(String resource) {
        try {
            InputStream in = getClass().getResourceAsStream(resource);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            numCols = Integer.parseInt(br.readLine());
            numRows = Integer.parseInt(br.readLine());
            map = new int[numCols][numRows];
            width = numCols * tileSize;
            height = numCols * tileSize;

            String delims = "\\s+";
            for (int row = 0; row < numRows; row++) {
                String line = br.readLine();
                String[] tokens = line.split(delims);
                for (int col = 0; col < numCols; col++) {
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
