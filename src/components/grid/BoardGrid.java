package components.grid;

import components.BoardTile;
import utils.Utils;
import utils.helpers.Crush;
import utils.Level;
import utils.helpers.LevelType;
import utils.helpers.TileType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class BoardGrid extends JPanel {

    private BoardGridModel model;
    private final BoardGridUI ui;

    private Level level;
    private LevelType levelType;

    private int tilesXAxis, tilesYAxis;

    private ArrayList<ArrayList<BoardTile>> tiles = new ArrayList<>();
    private BoardTile tileDragStart, tileDragEnd;

    public BoardGrid(Level level) {
        this.level = level;

        this.tilesXAxis = level.getNumRows();
        this.tilesYAxis = level.getNumColumns();

        this.model = new BoardGridModel();
        this.model.addChangeListener((e -> repaint()));

        this.ui = new BoardGridUI(level, this);
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

    /**
     * Update the tiles on the board grid to show the new ones after
     * grouping at least 3 of them.
     *
     * @param potentialCrush - Object that stores the crush to explode.
     */
    public void crushed(Crush potentialCrush) {
        ArrayList<ArrayList<BoardTile>> changedTiles = getTiles();
        for (BoardTile tile : potentialCrush.getCrushedCandies()) {
            int row = tile.getTileRow();
            int col = tile.getTileCol();
            ArrayList<BoardTile> changedRow = tiles.get(row);
            changedRow.set(col, tile);

            changedTiles.set(row, changedRow);
        }

        setTiles(changedTiles);
    }

    public void removeCandies(ArrayList<BoardTile> crushedCandies) {
        for (BoardTile tile : crushedCandies) {
            tiles.get(tile.getTileRow()).get(tile.getTileCol()).setTileType(TileType.CRUSHED);
        }
        dropCandies();
        repaint();
    }

    public void dropCandies() {

        int[] crushedInCol = new int[tiles.size()];
        int[] minCrushRow = new int[tiles.size()];
        boolean[] colUpdating = new boolean[tiles.size()];
        ArrayList<Integer> tileInitValY = new ArrayList<>();
        ArrayList<ArrayList<BoardTile>> oldTiles = tiles;

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
                tileInitValY.add(tiles.get(minCrushRow[i]-1).get(i).getTileY());
            }
        }

        ArrayList<ArrayList<BoardTile>> newTiles = generateNewTiles(crushedInCol, minCrushRow);
        updateRowsCols(crushedInCol, minCrushRow, newTiles);

        Timer dropTimer = new Timer(0, e -> {
            boolean updating = false;
            for (int i = 0; i < crushedInCol.length; i++) {
                int spaceToMove = crushedInCol[i] * Utils.getTileSize();
                if (minCrushRow[i] > 0) {
                    for (int j = minCrushRow[i]+crushedInCol[i]-1; j >= 0; j--) {
                        if (colUpdating[i]) {
                            BoardTile tileToMove = tiles.get(j).get(i);
                            tileToMove.setTileY(tileToMove.getTileY()+Utils.getTileSize()/10);
                        }
                    }
                    if (tiles.get(minCrushRow[i]+crushedInCol[i]-1).get(i).getTileY() - tileInitValY.get(i) == spaceToMove) {
                        colUpdating[i] = false;
                    }
                }
            }
            repaint();
            for (boolean colUpdate : colUpdating) {
                if (colUpdate) updating = true;
            }
            if (!updating) {
                ((Timer) e.getSource()).stop();
            }
        });
        dropTimer.setRepeats(true);
        dropTimer.setInitialDelay(0);
        dropTimer.start();

    }

    private void updateRowsCols(int[] crushedInCol, int[] minCrushRow, ArrayList<ArrayList<BoardTile>> newTiles) {
        for (int i = 0; i < crushedInCol.length; i++) {
            if (minCrushRow[i] > 0) {
                for (int j = minCrushRow[i]-1; j >= 0; j--) {
                    BoardTile tileToUpdate = tiles.get(j).get(i);
                    tileToUpdate.setTileRow(j+crushedInCol[i]);
                    tiles.get(j+crushedInCol[i]).set(i, tileToUpdate);
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
                newTiles.get(i).add(new BoardTile(type, j-1, i,
                        i*Utils.getTileSize(), -(crushedInCol[i]-j+1)*Utils.getTileSize()));
                System.out.println(type);
            }
        }
        return newTiles;
    }

}
