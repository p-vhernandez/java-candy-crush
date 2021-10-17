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

    private final BoardGridModel model;
    private final BoardGridUI ui;

    private int tilesXAxis, tilesYAxis;

    private ArrayList<ArrayList<BoardTile>> tiles = new ArrayList<>();
    private BoardTile tileDragStart, tileDragEnd;
    ArrayList<BoardTile> crushedCandies;

    public BoardGrid(Level level) {
        this.model = new BoardGridModel();
        this.model.setLevel(level);
        this.model.addChangeListener((e -> repaint()));

        this.tilesXAxis = level.getNumRows();
        this.tilesYAxis = level.getNumColumns();

        this.ui = new BoardGridUI(this);
        this.ui.initializeUI();

        enableBoardGrid(false);
        setUpListeners();
    }

    private void setUpListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (model.isEnabled()) {
                    setTileDragStart(ui.getTile(getTiles(), e.getX(), e.getY()));
                    model.setPressed(true);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (model.isEnabled()) {
                    model.setPressed(false);
                    ui.setDragOne(false);
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                BoardTile tileDragEnd = ui.getTile(getTiles(), e.getX(), e.getY());
                if (tileDragEnd != null) {
                    if (ui.isDragValid(tileDragEnd)) {
                        ui.setDragOne(true);
                        setTileDragEnd(tileDragEnd);
                        ui.generateSwipeMotion(e);
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

    public void removeCandies(ArrayList<BoardTile> crushedCandies) {

        this.crushedCandies = crushedCandies;

        for (BoardTile tile : crushedCandies) {
            tiles.get(tile.getTileRow()).get(tile.getTileCol()).setTileType(TileType.CRUSHED);
        }

        dropCandies();
        repaint();
    }

    public void dropCandies() {
        this.model.setEnabled(false);
        int[] crushedInCol = new int[this.model.getLevel().getNumColumns()];
        boolean[] colUpdating = new boolean[this.model.getLevel().getNumColumns()];
        ArrayList<ArrayList<BoardTile>> tilesToUpdateByCol = new ArrayList<>();

        for (int i = 0; i < this.model.getLevel().getNumColumns(); i++) {
            tilesToUpdateByCol.add(new ArrayList<>());
        }

        for (BoardTile candy : crushedCandies) {
            for (int i = candy.getTileRow()-1; i >= 0; i--) {
                BoardTile tile = tiles.get(i).get(candy.getTileCol());
                if (tile.getTileType() != TileType.CRUSHED && !tilesToUpdateByCol.get(candy.getTileCol()).contains(tile)) {
                    tilesToUpdateByCol.get(candy.getTileCol()).add(tile);

                }
            }
        }

        for (int i = 0; i < tiles.size(); i++) {
            for (int j = 0; j < tiles.get(i).size(); j++) {
                if (tiles.get(i).get(j).getTileType() == TileType.CRUSHED) {
                    crushedInCol[j]++;
                    if (!colUpdating[j]) colUpdating[j] = true;
                }
            }
        }

        ArrayList<ArrayList<BoardTile>> newTiles = generateNewTiles(crushedInCol, tilesToUpdateByCol);
        updateRowsCols(newTiles, tilesToUpdateByCol, crushedCandies);

        Timer dropTimer = new Timer(0, e -> {
            boolean updating = false;

            for (ArrayList<BoardTile> column : tilesToUpdateByCol) {
                for (BoardTile tile : column) {
                    if (tile.getTileRow() * Utils.getTileSize() > tile.getTileY()) {
                        tile.setTileY(tile.getTileY() + Utils.getTileSize() / 10);
                    }
                    if (colUpdating[tile.getTileCol()] && tiles.get(0).get(tile.getTileCol()).getTileY() == 0) {
                        colUpdating[tile.getTileCol()] = false;
                    }
                }
            }

            repaint();

            for (boolean colUpdate : colUpdating) {
                if (colUpdate) updating = true;
            }

            if (!updating) {
                ((Timer) e.getSource()).stop();
                this.ui.checkBoard();
            }
        });

        dropTimer.setRepeats(true);
        dropTimer.setInitialDelay(0);
        dropTimer.start();
    }

    private void updateRowsCols(ArrayList<ArrayList<BoardTile>> newTiles, ArrayList<ArrayList<BoardTile>> tilesToUpdateByCol, ArrayList<BoardTile> crushedCandies) {
        for (int i = 0; i < tilesToUpdateByCol.size(); i++) {
            for (int j = 0; j < tilesToUpdateByCol.get(i).size(); j++) {
                BoardTile tileToUpdate = tilesToUpdateByCol.get(i).get(j);
                if (tileToUpdate.getTileY() >= 0) {
                    int rowShift = 0;
                    for (BoardTile crushedCandy : crushedCandies) {
                        if (crushedCandy.getTileCol() == i && crushedCandy.getTileRow() > j) {
                            rowShift++;
                        }
                    }
                    tileToUpdate.setTileRow(tileToUpdate.getTileRow()+rowShift);
                    tiles.get(tileToUpdate.getTileRow()).set(i, tileToUpdate);
                }
            }
        }

        for (ArrayList<BoardTile> col : newTiles) {
            for (BoardTile tile : col) {
                tiles.get(tile.getTileRow()).set(tile.getTileCol(), tile);
            }
        }
    }

    public ArrayList<ArrayList<BoardTile>> generateNewTiles(int[] crushedInCol, ArrayList<ArrayList<BoardTile>> tilesToUpdate) {
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
                BoardTile newTile = new BoardTile(type, j - 1, i,
                        i * Utils.getTileSize(), -(crushedInCol[i] - j + 1) * Utils.getTileSize());
                newTiles.get(i).add(newTile);
                tilesToUpdate.get(i).add(newTile);

            }
        }
        return newTiles;
    }

}
