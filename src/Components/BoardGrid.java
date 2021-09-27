package Components;

import Helpers.LevelType;
import Helpers.TileType;
import Model.BoardGridModel;
import UI.BoardGridUI;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class BoardGrid extends JPanel{

    private BoardGridModel model;
    private BoardGridUI ui;
    private LevelType levelType;
    //how many rows and cols of tiles
    private int tilesXAxis;
    private int tilesYAxis;
    private BoardTile[][] tiles;
    private int tileSize = 30;
    private int iconSize = 35;
    //for grid handling
    private BoardTile tileDragStart;
    private BoardTile tileDragEnd;

    public BoardGrid(LevelType type, int tilesXAxis, int tilesYAxis) {
        this.tilesXAxis = tilesXAxis;
        this.tilesYAxis = tilesYAxis;
        this.levelType = type;
        tiles = new BoardTile[tilesYAxis][tilesXAxis];

        generateTiles();
        //addListeners();

        this.model = new BoardGridModel();
        this.model.addChangeListener((e -> repaint()));
        this.ui = new BoardGridUI();
        this.ui.generateTiles(tilesXAxis, tilesYAxis, this);
        this.ui.initializeUI(this);
    }

    public int getTilesXAxis() {
        return tilesXAxis;
    }

    public void setTilesXAxis(int tilesXAxis) {
        this.tilesXAxis = tilesXAxis;
    }

    public int getTilesYAxis() {
        return tilesYAxis;
    }

    public void setTilesYAxis(int tilesYAxis) {
        this.tilesYAxis = tilesYAxis;
    }

    public LevelType getLevelType() {
        return this.levelType;
    }

    public void setLevelType(LevelType type) {
        this.levelType = type;
    }

    public BoardTile[][] getTiles() {
        return tiles;
    }

    public void setTiles(BoardTile[][] tiles) {
        this.tiles = tiles;
    }

    // Layout
    public BoardTile getTileDragStart() {
        return tileDragStart;
    }

    public void setTileDragStart(BoardTile tileDragStart) {
        this.tileDragStart = tileDragStart;
    }

    public BoardTile getTileDragEnd() {
        return tileDragEnd;
    }

    public void setTileDragEnd(BoardTile tileDragEnd) {
        this.tileDragEnd = tileDragEnd;
    }

    public Dimension getMaximumSize() { return getPreferredSize(); }
    public Dimension getMinimumSize() { return getPreferredSize(); }
    public Dimension getPreferredSize() { return new Dimension(tileSize*tilesXAxis,tileSize*tilesYAxis); }

    // TODO: refine code
    private void generateTiles() {
        BoardTile[] row;
        Random random = new Random();
        TileType[] types = {
                TileType.PURPLE_LOLLI,
                TileType.ORANGE_CANDY,
                TileType.YELLOW_LOLLI,
                TileType.EYEBALL,
                TileType.PUMPKIN
        };

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
                row[j] = new BoardTile(type, i, j);
            }
            tiles[i] = row;
        }
    }
}
