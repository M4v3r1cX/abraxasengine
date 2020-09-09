package com.bsodsoftware.abraxas.engine.graphics;

import com.bsodsoftware.abraxas.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
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

        colOffset = (int)-this.x/tileSize;
        rowOffset = (int)-this.y/tileSize;
    }

    private void fixBounds(){
        if (x < xmin) x = xmin;
        if (x > xmax) x = xmax;
        if (y < ymin) y = ymin;
        if (y > ymax) y = ymax;
    }

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
            map = new int[numRows][numCols];
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

    public void draw(Graphics2D graphics) {
        for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
            if (row >= numRows) {
                break;
            }

            for (int col = colOffset; col < colOffset + numColsToDraw; col++) {
                if (col > numCols) {
                    break;
                }

                if (map[row][col] == 0) {
                    continue;
                }

                int rc = map[row][col];
                int r = rc / numTilesAcross;
                int c = rc % numTilesAcross;

                graphics.drawImage(tiles[r][c].getImage(), (int) x + col * tileSize, (int) y + row * tileSize, null);
            }
        }

    }
}
