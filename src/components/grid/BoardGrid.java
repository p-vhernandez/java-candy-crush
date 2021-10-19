package components.grid;

import components.BoardTile;
import org.jetbrains.annotations.NotNull;
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

    private BoardGridModel model;
    private BoardGridUI view;

    private ArrayList<ArrayList<BoardTile>> tiles = new ArrayList<>();
    private BoardTile tileDragStart, tileDragEnd;
    private ArrayList<BoardTile> crushedCandies;

    public BoardGrid(Level level) {
        this.model = null;
        this.model = new BoardGridModel();
        this.model.setLevel(level);
        this.model.addChangeListener((e -> repaint()));

        this.view = null;
        this.view = new BoardGridUI(this);
        this.view.initializeUI();

        initialize();
    }

    private void initialize() {
        updateAxis();
        enableBoardGrid(false);
        setUpListeners();
    }

    private void updateAxis() {
        this.model.setTilesXAxis(this.model.getLevel().getNumRows());
        this.model.setTilesYAxis(this.model.getLevel().getNumColumns());
    }

    private void setUpListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (model.isEnabled()) {
                    setTileDragStart(view.getTile(getTiles(), e.getX(), e.getY()));
                    model.setPressed(true);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (model.isEnabled()) {
                    model.setPressed(false);
                    view.setDragOne(false);
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                BoardTile tileDragEnd = view.getTile(getTiles(), e.getX(), e.getY());
                if (tileDragEnd != null) {
                    if (view.isDragValid(tileDragEnd)) {
                        view.setDragOne(true);
                        setTileDragEnd(tileDragEnd);
                        view.generateSwipeMotion(e);
                    }
                }
            }
        });
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
}