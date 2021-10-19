package components.grid;

import components.BoardTile;
import components.cards.CardGameplay;
import utils.Level;
import utils.helpers.Crush;
import utils.helpers.Explosion;
import utils.helpers.LevelType;
import utils.helpers.TileType;
import utils.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class BoardGridUI {

    private boolean dragInMotion = false;

    private final BoardGrid controller;

    private final HashMap<TileType, BufferedImage> icons = new HashMap<>();

    private final TileType[] types = new TileType[]{
            TileType.EYEBALL,
            TileType.PUMPKIN,
            TileType.ORANGE_CANDY,
            TileType.ROUND_LOLLI,
            TileType.SWIRL_LOLLI,
            TileType.MUMMY,
            TileType.POISON_GREEN,
            TileType.POISON_RED
    };

    private boolean dragDone = false;

    private Crush potentialCrush;

    public BoardGridUI(BoardGrid controller) {
        this.controller = controller;
    }

    public void initializeUI() {
        controller.setBackground(Utils.darkBackground);

        generateTileIcons();
        generateTiles(this.controller.getLevel());
    }

    public void paint(Graphics2D g) {
        clearBoard(g);
        repaintBoard(g);

        if (potentialCrush != null) {
            for (Explosion explosion : potentialCrush.getExplosions()) {
                explosion.draw(g);
            }
        }
    }

    protected void switchLevels() {
        Level level = this.controller.getLevel();
        generateTiles(level);
    }

    /**
     * Store the tile icons beforehand to
     * reduce execution time.
     */
    private void generateTileIcons() {
        for (TileType type : types) {
            BufferedImage icon;

            try {
                icon = ImageIO.read(Objects.requireNonNull(this.getClass().getResourceAsStream(
                        "../../resources/img/" + type + ".png")));
                icons.put(type, icon);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Clears the component before redrawing
     * & repaints the background.
     *
     * @param g - Graphics2D
     */
    private void clearBoard(Graphics2D g) {
        g.clearRect(0, 0, controller.getWidth(), controller.getHeight());
        g.setPaint(Utils.darkBackground);
        g.fillRect(0, 0, controller.getWidth(), controller.getHeight());
    }

    /**
     * Repaints the full grid.
     *
     * @param g - Graphics2D
     */
    private void repaintBoard(Graphics2D g) {
        for (ArrayList<BoardTile> tileRow : controller.getTiles()) {
            for (BoardTile tile : tileRow) {
                if (tile.getTileType() != TileType.EMPTY && tile.getTileType() != TileType.CRUSHED) {
                    paintTile(tile, g);
                }
            }
        }

    }

    private void paintTile(BoardTile tile, Graphics2D g) {
        int iconX = tile.getTileX() + (Utils.getTileSize() - Utils.getIconSize()) / 2;
        int iconY = tile.getTileY() + (Utils.getTileSize() - Utils.getIconSize()) / 2;

        g.setPaint(Utils.tileFill);
        g.fillRoundRect(tile.getTileX(), tile.getTileY(),
                Utils.getTileSize(), Utils.getTileSize(),
                5, 5);

        g.setPaint(Utils.tileBorder);
        g.drawRoundRect(tile.getTileX(), tile.getTileY(),
                Utils.getTileSize(), Utils.getTileSize(),
                5, 5);

        g.drawImage(icons.get(tile.getTileType()), iconX, iconY, Utils.getIconSize(), Utils.getIconSize(), null);
    }

    /**
     * Generates the tiles on the board.
     *
     * @param level - Level | object with all the level data
     */
    public void generateTiles(Level level) {
        ArrayList<ArrayList<BoardTile>> tiles = controller.getTiles();
        ArrayList<BoardTile> row;
        Random random = new Random();

        TileType[] types = {
                TileType.ROUND_LOLLI,
                TileType.ORANGE_CANDY,
                TileType.SWIRL_LOLLI,
                TileType.EYEBALL,
                TileType.PUMPKIN
        };

        for (int i = 0; i < level.getNumRows(); i++) {

            row = new ArrayList<>();

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

                    row.add(new BoardTile(type, i, j,
                            j * Utils.getTileSize(),
                            i * Utils.getTileSize()));
                } else {
                    row.add(new BoardTile(TileType.EMPTY, i, j,
                            j * Utils.getTileSize(),
                            i * Utils.getTileSize()));
                }
            }

            tiles.add(row);
        }

        controller.setTiles(tiles);
    }

    /**
     * First checking of the movement conditions (tile dragged,
     * tile that needs to be swiped, etc.) to see if the movement
     * can be carried out.
     *
     * @param e - Mouse event generated by the drag and drop
     */
    public void generateSwipeMotion(MouseEvent e) {
        BoardTile endTile = controller.getTileDragEnd();
        BoardTile startTile = controller.getTileDragStart();

        int endCol = endTile.getTileCol();
        int endRow = endTile.getTileRow();
        int startCol = startTile.getTileCol();
        int startRow = startTile.getTileRow();

        if (isOnePositionChangeHorizontally(startCol, endCol, startRow, endRow)
                || isOnePositionChangeVertically(startCol, endCol, startRow, endRow)) {
            if (!dragInMotion) {
                dragInMotion = true;
                int spaceToMove = Utils.getTileSize() / 4;

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
     * Swipes the tiles on the board.
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

            // Horizontal swipe
            if (isHorizontalMove(startTile, endTile)) {
                moveBothTiles(startTile, endTile, spaceToMove, true);

                if (startTile.getTileX() == initEndX
                        && endTile.getTileX() == initStartX) {
                    ((Timer) e.getSource()).stop();

                    updateGridTiles(startTile, endTile);
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

                if (startTile.getTileY() == initEndY
                        && endTile.getTileY() == initStartY) {
                    ((Timer) e.getSource()).stop();

                    updateGridTiles(startTile, endTile);
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

            // Check horizontally from position 0 to position
            // (length - 3) to find at least a group of 3 candies
            if (row >= 0 && row + 2 < controller.getTiles().size()) {

                type1 = tile.getTileType();
                type2 = controller.getTiles().get(row + 1).get(col).getTileType();
                type3 = controller.getTiles().get(row + 2).get(col).getTileType();

                if (type1 == type2 && type1 == type3) {
                    valid = true;

                    potentialCrush.addCrushedCandies((controller.getTiles().get(row + 2).get(col)));
                    potentialCrush.addCrushedCandies((controller.getTiles().get(row + 1).get(col)));
                    potentialCrush.addCrushedCandies(tile);
                }

            }

            // Check horizontally from position 1 to position
            // (length - 2) to find at least a group of 3 candies
            if (row >= 1 && row + 1 < controller.getTiles().size()) {

                type1 = controller.getTiles().get(row - 1).get(col).getTileType();
                type2 = tile.getTileType();
                type3 = controller.getTiles().get(row + 1).get(col).getTileType();

                if (type1 == type2 && type1 == type3) {
                    valid = true;

                    potentialCrush.addCrushedCandies(controller.getTiles().get(row + 1).get(col));
                    potentialCrush.addCrushedCandies(tile);
                    potentialCrush.addCrushedCandies(controller.getTiles().get(row - 1).get(col));
                }
            }

            // Check horizontally from position 2 until the end
            // to find at least a group of 3 candies
            if (row >= 2) {

                type3 = tile.getTileType();
                type1 = controller.getTiles().get(row - 1).get(col).getTileType();
                type2 = controller.getTiles().get(row - 2).get(col).getTileType();

                if (type1 == type2 && type1 == type3) {
                    valid = true;

                    potentialCrush.addCrushedCandies(tile);
                    potentialCrush.addCrushedCandies(controller.getTiles().get(row - 1).get(col));
                    potentialCrush.addCrushedCandies(controller.getTiles().get(row - 2).get(col));
                }
            }

            // Check vertically from position 0 to position
            // (length - 3) to find at least a group of 3 candies
            if (col >= 0 && col + 2 < controller.getTiles().get(0).size()) {

                type1 = tile.getTileType();
                type2 = controller.getTiles().get(row).get(col + 1).getTileType();
                type3 = controller.getTiles().get(row).get(col + 2).getTileType();

                if (type1 == type2 && type1 == type3) {
                    valid = true;

                    potentialCrush.addCrushedCandies(controller.getTiles().get(row).get(col + 2));
                    potentialCrush.addCrushedCandies(controller.getTiles().get(row).get(col + 1));
                    potentialCrush.addCrushedCandies(tile);
                }
            }

            // Check vertically from position 1 to position
            // (length - 2) to find at least a group of 3 candies
            if (col >= 1 && col + 1 < controller.getTiles().get(0).size()) {

                type1 = controller.getTiles().get(row).get(col - 1).getTileType();
                type2 = tile.getTileType();
                type3 = controller.getTiles().get(row).get(col + 1).getTileType();

                if (type1 == type2 && type1 == type3) {
                    valid = true;

                    potentialCrush.addCrushedCandies(controller.getTiles().get(row).get(col + 1));
                    potentialCrush.addCrushedCandies(tile);
                    potentialCrush.addCrushedCandies(controller.getTiles().get(row).get(col - 1));
                }
            }

            // Check vertically from position 2 until the end
            // to find at least a group of 3 candies
            if (col >= 2) {

                type3 = tile.getTileType();
                type1 = controller.getTiles().get(row).get(col - 1).getTileType();
                type2 = controller.getTiles().get(row).get(col - 2).getTileType();

                if (type1 == type2 && type1 == type3) {
                    valid = true;

                    potentialCrush.addCrushedCandies(tile);
                    potentialCrush.addCrushedCandies(controller.getTiles().get(row).get(col - 1));
                    potentialCrush.addCrushedCandies(controller.getTiles().get(row).get(col - 2));
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
            CardGameplay.oneMovementLess();
            potentialCrush.explode(controller);
            CardGameplay.updateScore(potentialCrush.getCrushedCandies().size());
        }
    }

    public void setDragOne(boolean drag) {
        this.dragDone = drag;
    }

    public BoardTile getTile(ArrayList<ArrayList<BoardTile>> tiles, int x, int y) {
        int tileSize = Utils.getTileSize();
        int col = (int) Math.floor(x / tileSize);
        int row = (int) Math.floor(y / tileSize);

        try {
            return tiles.get(row).get(col);
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

        controller.repaint();
    }

    private void updateGridTiles(BoardTile startTile, BoardTile endTile) {
        ArrayList<ArrayList<BoardTile>> tiles = controller.getTiles();

        int startRow = startTile.getTileRow();
        int endRow = endTile.getTileRow();
        startTile.setTileRow(endRow);
        endTile.setTileRow(startRow);

        int startCol = startTile.getTileCol();
        int endCol = endTile.getTileCol();
        startTile.setTileCol(endCol);
        endTile.setTileCol(startCol);

        tiles.get(startTile.getTileRow()).set(startTile.getTileCol(), startTile);
        tiles.get(endTile.getTileRow()).set(endTile.getTileCol(), endTile);

        controller.setTiles(tiles);
    }

    private boolean isHorizontalMove(BoardTile startTile, BoardTile endTile) {
        return startTile.getTileCol() != endTile.getTileCol();
    }

    private boolean notThreeInARowDimensionX(TileType type, int positionX, int positionY) {
        try {
            if (controller.getTiles().get(positionX - 1).get(positionY).getTileType() == TileType.EMPTY
                    || controller.getTiles().get(positionX - 2).get(positionY).getTileType() == TileType.EMPTY)
                return true;
            return type != controller.getTiles().get(positionX - 1).get(positionY).getTileType()
                    || type != controller.getTiles().get(positionX - 2).get(positionY).getTileType();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean notThreeInARowDimensionY(TileType type, ArrayList<BoardTile> row, int positionY) {
        if (row.get(positionY - 1).getTileType() == TileType.EMPTY
                || row.get(positionY - 2).getTileType() == TileType.EMPTY) return true;
        return type != row.get(positionY - 1).getTileType()
                || type != row.get(positionY - 2).getTileType();
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

    public boolean isDragValid(BoardTile tileDragEnd) {
        return controller.isEnabled()
                && (Math.abs(controller.getTileDragStart().getTileCol() - tileDragEnd.getTileCol()) == 1
                || Math.abs(controller.getTileDragStart().getTileRow() - tileDragEnd.getTileRow()) == 1)
                && !dragDone
                && tileDragEnd.getTileType() != TileType.EMPTY;
    }

    public void checkBoard() {
        Crush potentialCrush = new Crush();
        for (int i = 0; i < controller.getTiles().size(); i++) {
            for (int j = 0; j < controller.getTiles().get(i).size(); j++) {
                BoardTile tile = controller.getTiles().get(i).get(j);
                if (i >= 2 && !notThreeInARowDimensionX(tile.getTileType(), i, j)) {
                    potentialCrush.addCrushedCandies(tile);
                    potentialCrush.addCrushedCandies(controller.getTiles().get(i - 1).get(j));
                    potentialCrush.addCrushedCandies(controller.getTiles().get(i - 2).get(j));
                }
                if (j >= 2 && !notThreeInARowDimensionY(tile.getTileType(), controller.getTiles().get(i), j)) {
                    potentialCrush.addCrushedCandies(tile);
                    potentialCrush.addCrushedCandies(controller.getTiles().get(i).get(j - 1));
                    potentialCrush.addCrushedCandies(controller.getTiles().get(i).get(j - 2));
                }
            }
        }
        if (potentialCrush.getCrushedCandies().size() > 0) {
            potentialCrush.explode(controller);
            CardGameplay.updateScore(potentialCrush.getCrushedCandies().size());
        } else {
            controller.getModel().setEnabled(true);
        }
    }

}
