package UI;

import Components.BoardGrid;
import Components.BoardTile;
import Helpers.TileType;
import Model.BoardGridModel;
import utils.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class BoardGridUI {

    private final int tilesXAxis;
    private final int tilesYAxis;

    private boolean dragInMotion = false;

    private final BoardGrid grid;
    private final BoardGridModel model;

    public BoardGridUI(int tilesXAxis, int tilesYAxis, BoardGrid grid) {
        this.tilesXAxis = tilesXAxis;
        this.tilesYAxis = tilesYAxis;

        this.grid = grid;
        this.model = this.grid.getModel();

        generateTiles(tilesXAxis, tilesYAxis);
    }

    public void initializeUI() {
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
                generateSwipeMotion(e);
            }
        });
    }

    public void paint(Graphics2D g) {
        for (BoardTile[] tileRow : grid.getTiles()) {
            for (BoardTile tile : tileRow) {
                int iconX = tile.getTileX() + (Utils.getTileSize() - Utils.getIconSize()) / 2;
                int iconY = tile.getTileY() + (Utils.getTileSize() - Utils.getIconSize()) / 2;
                BufferedImage icon = null;

                try {
                    icon = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "../resources/img/" + tile.getTileType() + ".png")));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                g.setPaint(Utils.tileFill);
                g.fillRoundRect(tile.getTileX(), tile.getTileY(),
                        Utils.getTileSize(), Utils.getTileSize(),
                        5, 5);

                g.setPaint(Utils.tileBorder);
                g.drawRoundRect(tile.getTileX(), tile.getTileY(),
                        Utils.getTileSize(), Utils.getTileSize(),
                        5, 5);

                g.drawImage(icon, iconX, iconY, Utils.getIconSize(), Utils.getIconSize(), null);
            }
        }
    }

    /**
     * Generates the tiles on the board
     *
     * @param tilesXAxis - integer | number of rows
     * @param tilesYAxis - integer | number of columns
     */
    public void generateTiles(int tilesXAxis, int tilesYAxis) {
        BoardTile[][] tiles = grid.getTiles();
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

                if (i >= 2) {
                    validX = false;
                }

                if (j >= 2) {
                    validY = false;
                }

                while (!validX) {
                    if (type != tiles[i - 1][j].getTileType()
                            || type != tiles[i - 2][j].getTileType()) {
                        validX = true;
                    } else {
                        type = types[random.nextInt(5)];
                    }
                }

                while (!validY) {
                    if (type != row[j - 1].getTileType()
                            || type != row[j - 2].getTileType()) {
                        validY = true;
                    } else {
                        type = types[random.nextInt(5)];
                    }
                }

                row[j] = new BoardTile(type, i, j,
                        j * Utils.getTileSize(),
                        i * Utils.getTileSize());
            }

            tiles[i] = row;
        }

        grid.setTiles(tiles);
    }

    private void generateSwipeMotion(MouseEvent e) {
        BoardTile endTile = getTile(grid.getTiles(), e.getX(), e.getY());
        BoardTile startTile = grid.getTileDragStart();

        int endCol = endTile.getTileCol();
        int endRow = endTile.getTileRow();
        int startCol = startTile.getTileCol();
        int startRow = startTile.getTileRow();

        if (isOnePositionChangeHorizontally(startCol, endCol, startRow, endRow)
                || isOnePositionChangeVertically(startCol, endCol, startRow, endRow)) {
            if (!dragInMotion) {
                dragInMotion = true;
                int spaceToMove = 15;

                if (isBackwardsMovement(startTile, endTile)) {
                    spaceToMove = -spaceToMove;
                }

                swipeTiles(startTile, endTile,
                        startTile.getTileX(), startTile.getTileY(),
                        endTile.getTileX(), endTile.getTileY(),
                        spaceToMove, true);
            }
        }
    }

    private boolean isOnePositionChangeHorizontally(int startCol, int endCol,
                                                    int startRow, int endRow) {
        return Math.abs(startCol - endCol) == 1 && startRow == endRow;
    }

    private boolean isOnePositionChangeVertically(int startCol, int endCol,
                                                  int startRow, int endRow) {
        return Math.abs(startRow - endRow) == 1 && startCol == endCol;
    }

    private boolean isBackwardsMovement(BoardTile startTile, BoardTile endTile) {
        return startTile.getTileCol() > endTile.getTileCol()
                || startTile.getTileRow() > endTile.getTileRow();
    }

    private BoardTile getTile(BoardTile[][] tiles, int x, int y) {
        int tileSize = Utils.getTileSize();
        int col = (int) Math.floor(x / tileSize);
        int row = (int) Math.floor(y / tileSize);

        return tiles[row][col];
    }

    /**
     * Swipes the tiles on the board
     *
     * @param startTile         - tile where the movement started
     * @param endTile           - tile where the movement ended
     * @param initStartX        - initial X point of startTile
     * @param initStartY        - initial Y point of startTile
     * @param initEndX          - initial X point of endTile
     * @param initEndY          - initial Y point of endTile
     * @param spaceToMove       - space that the tiles need to be moved each iteration
     * @param pendingValidation - if it is needed to check if the movement is allowed
     */
    private void swipeTiles(BoardTile startTile, BoardTile endTile,
                            int initStartX, int initStartY, int initEndX, int initEndY,
                            int spaceToMove, boolean pendingValidation) {

        Timer myTimer = new Timer(0, e -> {
            BoardTile[][] tiles;

            // Horizontal swipe
            if (isHorizontalMove(startTile, endTile)) {
                startTile.setTileX(startTile.getTileX() + spaceToMove);
                endTile.setTileX(endTile.getTileX() - spaceToMove);

                tiles = grid.getTiles();
                tiles[startTile.getTileRow()][startTile.getTileCol()] = startTile;
                tiles[endTile.getTileRow()][endTile.getTileCol()] = endTile;

                grid.setTiles(tiles);

                if (startTile.getTileX() == initEndX
                        && endTile.getTileX() == initStartX) {
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

                    if (pendingValidation)
                        validateSwipe(
                                new BoardTile[]{
                                        startTile,
                                        endTile
                                },
                                tiles, startTile, endTile, spaceToMove);
                }

                // Vertical swipe
            } else {
                startTile.setTileY(startTile.getTileY() + spaceToMove);
                endTile.setTileY(endTile.getTileY() - spaceToMove);

                tiles = grid.getTiles();
                tiles[startTile.getTileRow()][startTile.getTileCol()] = startTile;
                tiles[endTile.getTileRow()][endTile.getTileCol()] = endTile;

                grid.setTiles(tiles);

                if (startTile.getTileY() == initEndY
                        && endTile.getTileY() == initStartY) {
                    ((Timer) e.getSource()).stop();

                    tiles = grid.getTiles();
                    tiles[startTile.getTileRow()][startTile.getTileCol()] = endTile;
                    tiles[endTile.getTileRow()][endTile.getTileCol()] = startTile;

                    int startRow = startTile.getTileRow();
                    int endRow = endTile.getTileRow();

                    startTile.setTileRow(endRow);
                    endTile.setTileRow(startRow);

                    grid.setTiles(tiles);
                    dragInMotion = false;

                    if (pendingValidation) {
                        validateSwipe(
                                new BoardTile[]{startTile, endTile},
                                tiles, endTile, startTile, -spaceToMove);

                    }
                }
            }
        });

        myTimer.setInitialDelay(0);
        myTimer.start();
    }

    private boolean isHorizontalMove(BoardTile startTile, BoardTile endTile) {
        return startTile.getTileCol() != endTile.getTileCol();
    }

    /**
     * Check if the movement is valid.
     * The movement is valid when it creates a group of - at least -
     * three candies of the same type.
     *
     * @param tilesToValidate - tiles that needed to be checked
     * @param tiles           - tiles present in the board
     * @param startTile       - tile where the movement started
     * @param endTile         - tile where the movement ended
     * @param spaceToMove     - space that the tiles need to be moved each iteration
     */
    private void validateSwipe(BoardTile[] tilesToValidate, BoardTile[][] tiles,
                               BoardTile startTile, BoardTile endTile, int spaceToMove) {

        boolean valid = false;

        for (BoardTile tile : tilesToValidate) {
            int row = tile.getTileRow();
            int col = tile.getTileCol();

            TileType type1;
            TileType type2;
            TileType type3;

            if (row >= 0 && row + 2 < tiles.length) {
                type1 = tile.getTileType();
                type2 = tiles[row + 1][col].getTileType();
                type3 = tiles[row + 2][col].getTileType();

                if (type1 == type2 && type1 == type3) {
                    valid = true;
                }
            }

            if (row >= 1 && row + 1 < tiles.length) {
                type1 = tiles[row - 1][col].getTileType();
                type2 = tile.getTileType();
                type3 = tiles[row + 1][col].getTileType();

                if (type1 == type2 && type1 == type3) {
                    valid = true;
                }
            }

            if (row >= 2) {
                type1 = tiles[row - 1][col].getTileType();
                type2 = tiles[row - 2][col].getTileType();
                type3 = tile.getTileType();

                if (type1 == type2 && type1 == type3) {
                    valid = true;
                }
            }

            if (col >= 0 && col + 2 < tiles[0].length) {
                type1 = tile.getTileType();
                type2 = tiles[row][col + 1].getTileType();
                type3 = tiles[row][col + 2].getTileType();

                if (type1 == type2 && type1 == type3) {
                    valid = true;
                }
            }

            if (col >= 1 && col + 1 < tiles[0].length) {
                type1 = tiles[row][col - 1].getTileType();
                type2 = tile.getTileType();
                type3 = tiles[row][col + 1].getTileType();

                if (type1 == type2 && type1 == type3) {
                    valid = true;
                }
            }

            if (col >= 2) {
                type1 = tiles[row][col - 1].getTileType();
                type2 = tiles[row][col - 2].getTileType();
                type3 = tile.getTileType();

                if (type1 == type2 && type1 == type3) {
                    valid = true;
                }
            }
        }

        /*
         *  If the movement is not valid,
         *  swipe back the tiles to the original place
         */
        if (!valid) {
            swipeTiles(endTile, startTile,
                    endTile.getTileX(), endTile.getTileY(),
                    startTile.getTileX(), startTile.getTileY(),
                    spaceToMove, false);
        }
    }
}
