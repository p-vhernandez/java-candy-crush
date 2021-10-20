package components.grid;

import components.BoardTile;
import components.cards.CardGameplay;
import utils.Utils;
import utils.Level;
import utils.helpers.TileType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class BoardGrid extends JPanel {

    private final CardGameplay container;
    private final BoardGridModel model;
    private final BoardGridUI view;

    private ArrayList<ArrayList<BoardTile>> tiles = new ArrayList<>();
    private BoardTile tileDragStart, tileDragEnd;
    private ArrayList<BoardTile> crushedCandies;

    public BoardGrid(Level level, CardGameplay container) {
        this.container = container;

        this.model = new BoardGridModel();
        this.model.setLevel(level);
        this.model.addChangeListener((e -> repaint()));

        this.view = new BoardGridUI(this);
        this.view.initializeUI();

        initialize();
    }

    private void initialize() {
        updateAxis();
        enableBoardGrid(false);
    }

    private void updateAxis() {
        this.model.setTilesXAxis(this.model.getLevel().getNumRows());
        this.model.setTilesYAxis(this.model.getLevel().getNumColumns());
    }

    public Level getLevel() {
        return this.model.getLevel();
    }

    public void setLevel(Level level) {
        this.model.setLevel(level);
    }

    public ArrayList<ArrayList<BoardTile>> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<ArrayList<BoardTile>> tiles) {
        this.tiles = tiles;
        repaint();
    }

    public BoardGridModel getModel() {
        return model;
    }

    public void setEnabled(boolean enabled) {
        this.model.setEnabled(enabled);
    }

    public boolean isEnabled() {
        return this.model.isEnabled();
    }

    public void setPressed(boolean pressed) {
        this.model.setPressed(pressed);
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

    public ArrayList<BoardTile> getCrushedCandies() {
        return crushedCandies;
    }

    public void setCrushedCandies(ArrayList<BoardTile> crushedCandies) {
        this.crushedCandies = crushedCandies;
    }

    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    public Dimension getPreferredSize() {
        int boardWidth = Utils.getTileSize() * this.model.getTilesYAxis();
        int boardHeight = Utils.getTileSize() * this.model.getTilesXAxis();

        return new Dimension(boardWidth, boardHeight);
    }

    @Override
    public void paintComponent(Graphics g) {
        this.view.paint((Graphics2D) g);
    }

    public void enableBoardGrid(boolean enabled) {
        this.model.setEnabled(enabled);
    }

    public void removeCandies(ArrayList<BoardTile> crushedCandies) {
        this.view.removeCandies(crushedCandies);
    }

    protected void checkEndOfGame() {
        container.checkEndOfGame();
    }

}