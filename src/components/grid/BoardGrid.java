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
    ArrayList<BoardTile> crushedCandies;

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

        for (ArrayList<BoardTile> boardTiles : tiles) {
            for (int j = 0; j < boardTiles.size(); j++) {
                if (boardTiles.get(j).getTileType() == TileType.CRUSHED) {
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
                if (colUpdate) {
                    updating = true;
                    break;
                }
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

    private void updateRowsCols(ArrayList<ArrayList<BoardTile>> newTiles, @NotNull ArrayList<ArrayList<BoardTile>> tilesToUpdateByCol, ArrayList<BoardTile> crushedCandies) {
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
                    try {
                        tiles.get(tileToUpdate.getTileRow()).set(i, tileToUpdate);
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        for (ArrayList<BoardTile> col : newTiles) {
            for (BoardTile tile : col) {
                tiles.get(tile.getTileRow()).set(tile.getTileCol(), tile);
            }
        }
        generateSpecialCandies(crushedCandies);
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

        for (int i = 0; i < getLevel().getNumColumns(); i++) {
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

    private void generateSpecialCandies(@NotNull ArrayList<BoardTile> crushedCandies) {
        if (crushedCandies.size() > 3) {

            for (int i = 0; i < getLevel().getNumRows(); i++) {
                int[] data = crushedInRow(i);
                if (data[0] >= 3) {
                    for (int j = data[3]; j < data[0] + data[3] - 1; j++) {
                        int crushedInMinCol;
                        crushedInMinCol = crushedInCol(j)[0];
                        if (crushedInMinCol >= 3) {
                            tiles.get(data[1]).get(j).setTileType(TileType.MUMMY);
                        }
                    }
                }
                if (data[0] == 4) {
                    tiles.get(data[1]).get(data[2]).setTileType(TileType.POISON_GREEN);
                }
                if (data[0] >= 5) {
                    tiles.get(data[1]).get(data[2]).setTileType(TileType.FIREWORK);
                }
            }

            //red poison or firework
            for (int i = 0; i < getLevel().getNumColumns(); i++) {
                int[] data = crushedInCol(i);
                if (data[0] == 4) {
                    tiles.get(data[1]).get(data[2]).setTileType(TileType.POISON_RED);
                } else if (data[0] >= 5) {
                    tiles.get(data[1]).get(data[2]).setTileType(TileType.FIREWORK);
                }
            }
        }
    }

    private int[] crushedInRow(int row) {
        int crushedInRow = 0;
        int replaceRow = -1;
        int replaceCol = -1;
        int minCol = 1000;
        for (BoardTile crushedCandy : crushedCandies) {
            if (crushedCandy.getTileRow() == row) {
                crushedInRow++;
                replaceRow = crushedCandy.getTileRow();
                replaceCol = crushedCandy.getTileCol();
                if (crushedCandy.getTileCol() < minCol) minCol = crushedCandy.getTileCol();
            }
        }
        return new int[]{crushedInRow, replaceRow, replaceCol, minCol};
    }

    private int[] crushedInCol(int col) {
        int crushedInCol = 0;
        int replaceRow = -1;
        int replaceCol = -1;
        int minRow = 1000;

        for (BoardTile crushedCandy : crushedCandies) {
            if (crushedCandy.getTileCol() == col) {
                crushedInCol++;
                replaceRow = crushedCandy.getTileRow();
                replaceCol = crushedCandy.getTileCol();
                if (crushedCandy.getTileRow() < minRow) minRow = crushedCandy.getTileRow();
            }
        }
        return new int[]{crushedInCol, replaceRow, replaceCol, minRow};
    }
}