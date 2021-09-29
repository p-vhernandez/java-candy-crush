package components.grid;

import components.BoardTile;
import main.CandyCrush;
import utils.Utils;
import utils.helpers.Level;
import utils.helpers.LevelType;

import javax.swing.*;
import java.awt.*;

public class BoardGrid extends JPanel {

    private BoardGridModel model;
    private final BoardGridUI ui;

    private Level level;
    private LevelType levelType;

    private int tilesXAxis, tilesYAxis;

    private BoardTile[][] tiles;
    private BoardTile tileDragStart, tileDragEnd;

    public BoardGrid(Level level) {
        this.level = level;

        this.tilesXAxis = level.getNumberRows();
        this.tilesYAxis = level.getNumColumns();

        this.tiles = new BoardTile[tilesYAxis][tilesXAxis];

        this.model = new BoardGridModel();
        this.model.addChangeListener((e -> repaint()));

        this.ui = new BoardGridUI(tilesXAxis, tilesYAxis, this);
        this.ui.initializeUI();

        enableBoardGrid(false);
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

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public BoardTile[][] getTiles() {
        return tiles;
    }

    public void setTiles(BoardTile[][] tiles) {
        this.tiles = tiles;
        repaint();
    }

    public BoardGridModel getModel() {
        return model;
    }

    public void setModel(BoardGridModel model) {
        this.model = model;
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

    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    public Dimension getPreferredSize() {
        int boardWidth = Utils.getTileSize() * tilesXAxis;
        int boardHeight = Utils.getTileSize() * tilesYAxis;

        return new Dimension(boardWidth, boardHeight);
    }

    public void paintComponent(Graphics g) {
        this.ui.paint((Graphics2D) g);
    }

    public void enableBoardGrid(boolean enabled) {
        this.model.setEnabled(enabled);
    }

}
