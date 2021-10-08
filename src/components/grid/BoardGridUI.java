package components.grid;

import components.BoardTile;
import components.CardGameplay;
import utils.Level;
import utils.helpers.Crush;
import utils.helpers.LevelType;
import utils.helpers.TileType;
import utils.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class BoardGridUI {

    private boolean dragInMotion = false;

    private final BoardGrid grid;
    private final BoardGridModel model;

    private boolean dragDone = false;

    private Crush potentialCrush;

    public BoardGridUI(Level level, BoardGrid grid) {
        this.grid = grid;
        this.model = this.grid.getModel();

        generateTiles(level);
    }

    public void initializeUI() {
        grid.setBackground(Utils.darkBackground);

        grid.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (grid.getModel().isEnabled()) {
                    grid.setTileDragStart(getTile(grid.getTiles(), e.getX(), e.getY()));
                    model.setPressed(true);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (grid.getModel().isEnabled()) {
                    model.setPressed(false);
                    dragDone = false;
                }
            }
        });

        grid.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                BoardTile tileDragEnd = getTile(grid.getTiles(), e.getX(), e.getY());
                if (tileDragEnd != null) {
                    if (isDragValid(tileDragEnd)) {
                        dragDone = true;
                        grid.setTileDragEnd(tileDragEnd);
                        generateSwipeMotion(e);
                    }
                }
            }
        });
    }

    public void paint(Graphics2D g) {

        //clears the component before redrawing and repaints the background
        g.clearRect(0, 0, grid.getWidth(), grid.getHeight());
        g.setPaint(Utils.darkBackground);
        g.fillRect(0,0, grid.getWidth(), grid.getHeight());

        for (BoardTile[] tileRow : grid.getTiles()) {
            for (BoardTile tile : tileRow) {
                if (tile.getTileType() != TileType.EMPTY) {

                    int iconX = tile.getTileX() + (Utils.getTileSize() - Utils.getIconSize()) / 2;
                    int iconY = tile.getTileY() + (Utils.getTileSize() - Utils.getIconSize()) / 2;
                    BufferedImage icon = null;

                    try {
                        icon = ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream(
                                "../../resources/img/" + tile.getTileType() + ".png")));
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
    }

    /**
     * Generates the tiles on the board
     *
     * @param level - Level | object with all the level data
     */
    public void generateTiles(Level level) {
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

        for (int i = 0; i < level.getNumRows(); i++) {

            row = new BoardTile[level.getNumColumns()];

            for (int j = 0; j < level.getNumColumns(); j++) {

                if (level.getLevelType() != LevelType.CROSS
                        || level.getLevelType() == LevelType.CROSS && j >= 2 && j <= 6
                        || level.getLevelType() == LevelType.CROSS && i >= 2 && i <= 5) {


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
                        if (notThreeInARowDimensionX(type, i, j)) {
                            validX = true;
                        } else {
                            type = types[random.nextInt(5)];
                        }
                    }

                    while (!validY) {
                        if (notThreeInARowDimensionY(type, row, j)) {
                            validY = true;
                        } else {
                            type = types[random.nextInt(5)];
                        }
                    }

                    row[j] = new BoardTile(type, i, j,
                            j * Utils.getTileSize(),
                            i * Utils.getTileSize());
                } else {
                    row[j] = new BoardTile(TileType.EMPTY, i, j,
                            j * Utils.getTileSize(),
                            i * Utils.getTileSize());
                }
            }

            tiles[i] = row;
        }

        grid.setTiles(tiles);
    }

    /**
     * First checking of the movement conditions (tile dragged,
     * tile that needs to be swiped, etc) to see if the movement
     * can be carried out.
     *
     * @param e - Mouse event generated by the drag and drop
     */
    private void generateSwipeMotion(MouseEvent e) {
        BoardTile endTile = grid.getTileDragEnd();
        BoardTile startTile = grid.getTileDragStart();

        int endCol = endTile.getTileCol();
        int endRow = endTile.getTileRow();
        int startCol = startTile.getTileCol();
        int startRow = startTile.getTileRow();

        if (isOnePositionChangeHorizontally(startCol, endCol, startRow, endRow)
                || isOnePositionChangeVertically(startCol, endCol, startRow, endRow)) {
            if (!dragInMotion) {
                dragInMotion = true;
                int spaceToMove = Utils.getTileSize() / 2;

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
                moveBothTiles(startTile, endTile, spaceToMove, true);
                updateGridTiles(startTile, endTile);

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

                    if (pendingValidation) {
                        validateSwipe(
                                new BoardTile[]{startTile, endTile},
                                startTile, endTile, spaceToMove);
                    }
                }
            } else {
                // Vertical swipe
                moveBothTiles(startTile, endTile, spaceToMove, false);

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
                                endTile, startTile, -spaceToMove);

                    }
                }
            }
        });

        myTimer.setInitialDelay(0);
        myTimer.start();
    }

    /**
     * Check if the movement is valid.
     * The movement is valid when it creates a group of - at least -
     * three candies of the same type.
     *
     * @param tilesToValidate - tiles that needed to be checked
     * @param startTile       - tile where the movement started
     * @param endTile         - tile where the movement ended
     * @param spaceToMove     - space that the tiles need to be moved each iteration
     */
    private void validateSwipe(BoardTile[] tilesToValidate,
                               BoardTile startTile, BoardTile endTile, int spaceToMove) {

        boolean valid = false;
        potentialCrush = new Crush();

        for (BoardTile tile : tilesToValidate) {
            int row = tile.getTileRow();
            int col = tile.getTileCol();

            TileType type1, type2, type3;

            ArrayList<Crush> crushes = new ArrayList<>();

            // Check horizontally from position 0 to position
            // (length - 3) to find at least a group of 3 candies
            if (row >= 0 && row + 2 < grid.getTiles().length) {

                type1 = tile.getTileType();
                type2 = grid.getTiles()[row + 1][col].getTileType();
                type3 = grid.getTiles()[row + 2][col].getTileType();

                if (type1 == type2 && type1 == type3) {
                    valid = true;

                    potentialCrush.addCrushedCandies(tile);
                    potentialCrush.addCrushedCandies((grid.getTiles()[row + 1][col]));
                    potentialCrush.addCrushedCandies((grid.getTiles()[row + 2][col]));
                }

            }

            // Check horizontally from position 1 to position
            // (length - 2) to find at least a group of 3 candies
            if (row >= 1 && row + 1 < grid.getTiles().length) {

                type1 = grid.getTiles()[row - 1][col].getTileType();
                type2 = tile.getTileType();
                type3 = grid.getTiles()[row + 1][col].getTileType();

                if (type1 == type2 && type1 == type3) {
                    valid = true;

                    potentialCrush.addCrushedCandies(grid.getTiles()[row - 1][col]);
                    potentialCrush.addCrushedCandies(tile);
                    potentialCrush.addCrushedCandies(grid.getTiles()[row + 1][col]);
                }
            }

            // Check horizontally from position 2 until the end
            // to find at least a group of 3 candies
            if (row >= 2) {

                type1 = grid.getTiles()[row - 1][col].getTileType();
                type2 = grid.getTiles()[row - 2][col].getTileType();
                type3 = tile.getTileType();

                if (type1 == type2 && type1 == type3) {
                    valid = true;

                    potentialCrush.addCrushedCandies(grid.getTiles()[row - 1][col]);
                    potentialCrush.addCrushedCandies(grid.getTiles()[row - 2][col]);
                    potentialCrush.addCrushedCandies(tile);
                }
            }

            // Check vertically from position 0 to position
            // (length - 3) to find at least a group of 3 candies
            if (col >= 0 && col + 2 < grid.getTiles()[0].length) {

                type1 = tile.getTileType();
                type2 = grid.getTiles()[row][col + 1].getTileType();
                type3 = grid.getTiles()[row][col + 2].getTileType();

                if (type1 == type2 && type1 == type3) {
                    valid = true;

                    potentialCrush.addCrushedCandies(tile);
                    potentialCrush.addCrushedCandies(grid.getTiles()[row][col + 1]);
                    potentialCrush.addCrushedCandies(grid.getTiles()[row][col + 2]);
                }
            }

            // Check vertically from position 1 to position
            // (length - 2) to find at least a group of 3 candies
            if (col >= 1 && col + 1 < grid.getTiles()[0].length) {

                type1 = grid.getTiles()[row][col - 1].getTileType();
                type2 = tile.getTileType();
                type3 = grid.getTiles()[row][col + 1].getTileType();

                if (type1 == type2 && type1 == type3) {
                    valid = true;

                    potentialCrush.addCrushedCandies(grid.getTiles()[row][col - 1]);
                    potentialCrush.addCrushedCandies(tile);
                    potentialCrush.addCrushedCandies(grid.getTiles()[row][col + 1]);
                }
            }

            // Check vertically from position 2 until the end
            // to find at least a group of 3 candies
            if (col >= 2) {

                type1 = grid.getTiles()[row][col - 1].getTileType();
                type2 = grid.getTiles()[row][col - 2].getTileType();
                type3 = tile.getTileType();

                if (type1 == type2 && type1 == type3) {
                    valid = true;

                    potentialCrush.addCrushedCandies(grid.getTiles()[row][col - 1]);
                    potentialCrush.addCrushedCandies(grid.getTiles()[row][col - 2]);
                    potentialCrush.addCrushedCandies(tile);
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
        } else {
            // TODO: get rid of group of candies
            CardGameplay.oneMovementLess();
            potentialCrush.crushCandies();
            grid.crushed(potentialCrush);
            CardGameplay.updateScore(potentialCrush.getCrushedCandies().size());
        }
    }

    private BoardTile getTile(BoardTile[][] tiles, int x, int y) {
        int tileSize = Utils.getTileSize();
        int col = (int) Math.floor(x / tileSize);
        int row = (int) Math.floor(y / tileSize);
        try {
            return tiles[row][col];
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    private void moveBothTiles(BoardTile startTile, BoardTile endTile, int spaceToMove, boolean horizontally) {
        if (horizontally) {
            startTile.setTileX(startTile.getTileX() + spaceToMove);
            endTile.setTileX(endTile.getTileX() - spaceToMove);
        } else {
            startTile.setTileY(startTile.getTileY() + spaceToMove);
            endTile.setTileY(endTile.getTileY() - spaceToMove);
        }
    }

    private void updateGridTiles(BoardTile startTile, BoardTile endTile) {
        BoardTile[][] tiles = grid.getTiles();
        tiles[startTile.getTileRow()][startTile.getTileCol()] = startTile;
        tiles[endTile.getTileRow()][endTile.getTileCol()] = endTile;
        grid.setTiles(tiles);
    }

    private void addTilesToCrush(ArrayList<BoardTile> tiles) {
        for (BoardTile tile : tiles) {
            potentialCrush.addCrushedCandies(tile);
        }
    }

    private boolean isHorizontalMove(BoardTile startTile, BoardTile endTile) {
        return startTile.getTileCol() != endTile.getTileCol();
    }

    private boolean notThreeInARowDimensionX(TileType type, int positionX, int positionY) {
        if (grid.getTiles()[positionX - 1][positionY].getTileType() == TileType.EMPTY
                || grid.getTiles()[positionX - 2][positionY].getTileType() == TileType.EMPTY)
            return true;
        return type != grid.getTiles()[positionX - 1][positionY].getTileType()
                || type != grid.getTiles()[positionX - 2][positionY].getTileType();
    }

    private boolean notThreeInARowDimensionY(TileType type, BoardTile[] row, int positionY) {
        if (row[positionY - 1].getTileType() == TileType.EMPTY
                || row[positionY - 2].getTileType() == TileType.EMPTY) return true;
        return type != row[positionY - 1].getTileType()
                || type != row[positionY - 2].getTileType();
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

    private boolean isDragValid(BoardTile tileDragEnd) {
        return grid.getModel().isEnabled()
                && (Math.abs(grid.getTileDragStart().getTileCol() - tileDragEnd.getTileCol()) == 1
                || Math.abs(grid.getTileDragStart().getTileRow() - tileDragEnd.getTileRow()) == 1)
                && !dragDone
                && tileDragEnd.getTileType() != TileType.EMPTY;
    }

}
