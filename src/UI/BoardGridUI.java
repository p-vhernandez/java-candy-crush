package UI;

import Components.BoardGrid;
import Components.BoardTile;
import Helpers.Colors;
import Helpers.TileType;
import Model.BoardGridModel;
import Model.BoardTileModel;
import utils.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Random;

public class BoardGridUI {

    private int tilesXAxis;
    private int tilesYAxis;
    private boolean dragInMotion = false;

    public BoardGridUI(int tilesXAxis, int tilesYAxis, BoardGrid grid) {
        this.tilesXAxis = tilesXAxis;
        this.tilesYAxis = tilesYAxis;
        generateTiles(tilesXAxis, tilesYAxis, grid);
    }

    public void initializeUI(BoardGrid grid) {
        BoardGridModel model = grid.getModel();
        grid.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                model.setPressed(true);
                grid.setTileDragStart(getTile(grid.getTiles(), e.getX(), e.getY()));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                model.setPressed(false);
            }
        });

        grid.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                BoardTile endTile = getTile(grid.getTiles(), e.getX(), e.getY());
                BoardTile startTile = grid.getTileDragStart();
                int endCol = endTile.getTileCol();
                int endRow = endTile.getTileRow();
                int startCol = startTile.getTileCol();
                int startRow = startTile.getTileRow();
                if ((Math.abs(startCol-endCol) == 1 && startRow == endRow)|| (Math.abs(startRow-endRow) == 1) && startCol == endCol) {
                    //TO DO: swipe conditions
                    if (!dragInMotion) {
                        dragInMotion = true;
                        swipeTiles(startTile, endTile, grid, startTile.getTileX(), startTile.getTileY(), endTile.getTileX(), endTile.getTileY());
                    }
                }
            }
        });
    }

    public void paint(Graphics2D g, BoardGrid grid) {
        for (BoardTile[] tileRow : grid.getTiles()) {
            for (BoardTile tile : tileRow) {
                int iconX = tile.getTileX() + (Utils.getTileSize() - Utils.getIconSize())/2;
                int iconY = tile.getTileY() + (Utils.getTileSize() - Utils.getIconSize())/2;
                BufferedImage icon = null;
                try {
                    icon = ImageIO.read(getClass().getResourceAsStream("../resources/img/" + tile.getTileType() +".png"));
                } catch (IOException e){
                    e.printStackTrace();
                }
                g.setPaint(Colors.tileFill);
                g.fillRoundRect(tile.getTileX(), tile.getTileY(), Utils.getTileSize(), Utils.getTileSize(), 5,5);
                g.setPaint(Colors.tileBorder);
                g.drawRoundRect(tile.getTileX(), tile.getTileY(), Utils.getTileSize(), Utils.getTileSize(), 5,5);
                g.drawImage(icon, iconX, iconY, Utils.getIconSize(), Utils.getIconSize(), null);
            }
        }
    }

    //generates the tiles on the board
    public void generateTiles(int tilesXAxis, int tilesYAxis, BoardGrid grid) {
        BoardTile[][] tiles = grid.getTiles();
        BoardTile[] row;
        Random random = new Random();
        TileType[] types = {TileType.PURPLE_LOLLI, TileType.ORANGE_CANDY, TileType.YELLOW_LOLLI, TileType.EYEBALL, TileType.PUMPKIN};
        for (int i = 0; i < tilesXAxis; i++) {
            row = new BoardTile[tilesXAxis];
            for (int j = 0; j < tilesYAxis; j++) {
                TileType type = types[random.nextInt(5)];
                boolean validX = true;
                boolean validY = true;
                if (i >= 2) { validX = false; }
                if (j >= 2) { validY = false; }
                while (!validX) {
                    if (type != tiles[i-1][j].getTileType() || type != tiles[i-2][j].getTileType()) {
                        validX = true;
                    } else {
                        type = types[random.nextInt(5)];
                    }
                }
                while (!validY) {
                    if (type != row[j-1].getTileType() || type != row[j-2].getTileType()) {
                        validY = true;
                    } else {
                        type = types[random.nextInt(5)];
                    }
                }
                row[j] = new BoardTile(type, i, j, j*Utils.getTileSize(), i*Utils.getTileSize());
            }
            tiles[i] = row;
        }
        grid.setTiles(tiles);
    }

    private BoardTile getTile(BoardTile[][] tiles, int x, int y) {
        int tileSize = Utils.getTileSize();
        int col = (int)Math.floor(x / tileSize);
        int row = (int)Math.floor(y/tileSize);
        return tiles[row][col];
    }

    private void swipeTiles(BoardTile startTile, BoardTile endTile, BoardGrid grid, int initStartX, int initStartY, int initEndX, int initEndY) {


        Timer myTimer = new Timer(0, e -> {

            BoardTile[][] tiles;
            if (startTile.getTileCol() < endTile.getTileCol()) {

                startTile.setTileX(startTile.getTileX() + 15);
                endTile.setTileX(endTile.getTileX() - 15);
                tiles = grid.getTiles();
                tiles[startTile.getTileRow()][startTile.getTileCol()] = startTile;
                tiles[endTile.getTileRow()][endTile.getTileCol()] = endTile;
                grid.setTiles(tiles);

                if (startTile.getTileX() == initEndX && endTile.getTileX() == initStartX) {

                    ((Timer) e.getSource()).stop();
                    tiles = grid.getTiles();
                    tiles[startTile.getTileRow()][startTile.getTileCol()] = endTile;
                    tiles[endTile.getTileRow()][endTile.getTileCol()] = startTile;
                    int startCol = startTile.getTileCol();
                    int endCol = endTile.getTileCol();
                    startTile.setTileCol(endCol);
                    endTile.setTileCol(startCol);
                    grid.setTiles(tiles);
                    dragInMotion = false;
                }
            } else if (startTile.getTileCol() > endTile.getTileCol()) {
                startTile.setTileX(startTile.getTileX() - 1);
                endTile.setTileX(endTile.getTileX() + 1);
            }
        });
        myTimer.setInitialDelay(0);
        myTimer.start();
    }
}
