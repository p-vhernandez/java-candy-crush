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
        this.model = new BoardGridModel();
        this.model.addChangeListener((e -> repaint()));
        this.ui = new BoardGridUI(tilesXAxis, tilesYAxis, this);
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
        repaint();
    }

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

    public BoardGridModel getModel() {
        return model;
    }

    public void setModel(BoardGridModel model) {
        this.model = model;
    }

    //Layout
    public Dimension getMaximumSize() { return getPreferredSize(); }
    public Dimension getMinimumSize() { return getPreferredSize(); }
    public Dimension getPreferredSize() { return new Dimension(tileSize*tilesXAxis,tileSize*tilesYAxis); }

    public void paintComponent(Graphics g) {
        this.ui.paint((Graphics2D) g, this);
    }

}
