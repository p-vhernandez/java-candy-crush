package components.grid;

import components.BoardTile;
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
    private ArrayList<ArrayList<BoardTile>> crushedCandiesByCol = new ArrayList<>();

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

    public void switchLevels() {
        updateAxis();

        this.view.switchLevels();
        this.model.setEnabled(true);
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

    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    public Dimension getPreferredSize() {
        int boardWidth = Utils.getTileSize() * this.model.getTilesXAxis();
        int boardHeight = Utils.getTileSize() * this.model.getTilesYAxis();

        return new Dimension(boardWidth, boardHeight);
    }

    public void paintComponent(Graphics g) {
        this.view.paint((Graphics2D) g);
    }

    public void enableBoardGrid(boolean enabled) {
        this.model.setEnabled(enabled);
    }

    public void removeCandies(ArrayList<BoardTile> crushedCandies) {
        for (int i = 0; i < this.model.getLevel().getNumColumns(); i++) {
            crushedCandiesByCol.add(new ArrayList<>());
        }

        for (BoardTile tile : crushedCandies) {
            tiles.get(tile.getTileRow()).get(tile.getTileCol()).setTileType(TileType.CRUSHED);
            crushedCandiesByCol.get(tile.getTileCol()).add(tile);
        }

        dropCandies();
        repaint();
    }

    public void dropCandies() {
        this.model.setEnabled(false);
        int[] crushedInCol = new int[this.model.getLevel().getNumColumns()];
        int[] minCrushRow = new int[this.model.getLevel().getNumColumns()];
        int[] crushesInCol = new int[this.model.getLevel().getNumColumns()];
        boolean[] colUpdating = new boolean[this.model.getLevel().getNumColumns()];
        ArrayList<Integer> tileInitValY = new ArrayList<>();
        ArrayList<ArrayList<BoardTile>> tilesToUpdateByCol = new ArrayList<>();

        //for ()

        for (int i = 0; i < tiles.size(); i++) {
            for (int j = 0; j < tiles.get(i).size(); j++) {
                if (tiles.get(i).get(j).getTileType() == TileType.CRUSHED) {
                    crushedInCol[j]++;
                    if (minCrushRow[j] == 0 && !colUpdating[j]) {
                        minCrushRow[j] = i;
                    }
                    if (!colUpdating[j]) colUpdating[j] = true;
                }
            }
        }

        for (int i = 0; i < crushedInCol.length; i++) {
            if (crushedInCol[i] == 0 || minCrushRow[i] == 0) {
                tileInitValY.add(0);
            } else {
                tileInitValY.add(tiles.get(minCrushRow[i] - 1).get(i).getTileY());
            }
        }

        ArrayList<ArrayList<BoardTile>> newTiles = generateNewTiles(crushedInCol, minCrushRow);
        updateRowsCols(crushedInCol, minCrushRow, newTiles);

        Timer dropTimer = new Timer(0, e -> {
            boolean updating = false;

            for (int i = 0; i < crushedInCol.length; i++) {
                int spaceToMove = crushedInCol[i] * Utils.getTileSize();

                if (minCrushRow[i] > 0) {
                    for (int j = minCrushRow[i] + crushedInCol[i] - 1; j >= 0; j--) {
                        if (colUpdating[i]) {
                            BoardTile tileToMove = tiles.get(j).get(i);
                            tileToMove.setTileY(tileToMove.getTileY() + Utils.getTileSize() / 10);
                        }
                    }
                    if (colUpdating[i] && tiles.get(minCrushRow[i] + crushedInCol[i] - 1).get(i).getTileY() - tileInitValY.get(i) == spaceToMove) {
                        colUpdating[i] = false;
                    }
                } else {
                    if (colUpdating[i]) {
                        for (BoardTile tile : newTiles.get(i)) {
                            tile.setTileY(tile.getTileY() + Utils.getTileSize() / 10);
                            if (colUpdating[i] && newTiles.get(i).get(0).getTileY() == 0) {
                                colUpdating[i] = false;
                            }
                        }
                    }
                }
            }

            repaint();

            for (boolean colUpdate : colUpdating) {
                if (colUpdate) updating = true;
            }

            if (!updating) {
                ((Timer) e.getSource()).stop();
                this.view.checkBoard();
            }
        });

        dropTimer.setRepeats(true);
        dropTimer.setInitialDelay(0);
        dropTimer.start();
    }

    private void updateRowsCols(int[] crushedInCol, int[] minCrushRow, ArrayList<ArrayList<BoardTile>> newTiles) {
        for (int i = 0; i < crushedInCol.length; i++) {
            if (minCrushRow[i] > 0) {
                for (int j = minCrushRow[i] - 1; j >= 0; j--) {
                    BoardTile tileToUpdate = tiles.get(j).get(i);
                    tileToUpdate.setTileRow(j + crushedInCol[i]);
                    tiles.get(j + crushedInCol[i]).set(i, tileToUpdate);
                }
            }
        }

        for (ArrayList<BoardTile> col : newTiles) {
            for (BoardTile tile : col) {
                tiles.get(tile.getTileRow()).set(tile.getTileCol(), tile);
            }
        }
    }

    public ArrayList<ArrayList<BoardTile>> generateNewTiles(int[] crushedInCol, int[] minCrushRow) {
        ArrayList<ArrayList<BoardTile>> newTiles = new ArrayList<>();
        Random random = new Random();
        TileType[] types = {
                TileType.ROUND_LOLLI,
                TileType.ORANGE_CANDY,
                TileType.SWIRL_LOLLI,
                TileType.EYEBALL,
                TileType.PUMPKIN
        };

        for (int i = 0; i < crushedInCol.length; i++) {
            newTiles.add(new ArrayList<>());
            for (int j = 1; j <= crushedInCol[i]; j++) {
                TileType type = types[random.nextInt(5)];
                newTiles.get(i).add(new BoardTile(type, j - 1, i,
                        i * Utils.getTileSize(), -(crushedInCol[i] - j + 1) * Utils.getTileSize()));
            }
        }

        return newTiles;
    }

}
