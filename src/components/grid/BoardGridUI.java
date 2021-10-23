package components.grid;

import utils.BoardTile;
import components.cards.CardGameplay;
import utils.Level;
import utils.helpers.Crush;
import utils.helpers.Explosion;
import utils.helpers.LevelType;
import utils.helpers.TileType;
import utils.Utils;

import javax.imageio.ImageIO;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

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
            TileType.POISON_RED,
            TileType.FIREWORK
    };

    private boolean dragDone = false;

    private Crush potentialCrush;
    private ArrayList<int[]> specialCoordinates = new ArrayList<>();
    private ArrayList<TileType> specialTypes = new ArrayList<>();

    public BoardGridUI(BoardGrid controller) {
        this.controller = controller;
    }

    public void initializeUI() {
        controller.setBackground(Utils.darkBackground);
        setUpListeners();

        generateTileIcons();
        generateTiles(this.controller.getLevel());
    }

    private void setUpListeners() {
        controller.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (controller.isEnabled()) {
                    controller.setTileDragStart(getTile(controller.getTiles(), e.getX(), e.getY()));
                    controller.getTileDragStart().setSelected(true);
                    controller.setPressed(true);
                    controller.repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (controller.isEnabled()) {
                    controller.getTileDragStart().setSelected(false);
                    controller.setPressed(false);
                    setDragOne(false);
                }
            }
        });

        controller.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                BoardTile tileDragEnd = getTile(controller.getTiles(), e.getX(), e.getY());

                if (tileDragEnd != null) {
                    if (isDragValid(tileDragEnd)) {
                        controller.getTileDragStart().setSelected(false);
                        setDragOne(true);
                        controller.setTileDragEnd(tileDragEnd);
                        generateSwipeMotion(e);
                    }
                }
            }
        });
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

    /**
     * Store the tile icons beforehand to
     * reduce execution time.
     */
    private void generateTileIcons() {
        for (TileType type : types) {
            BufferedImage icon;

            try {
                icon = ImageIO.read(Objects.requireNonNull(BoardGridUI.class.getResourceAsStream(
                        "/resources/img/" + type + ".png")));
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

        if (tile.isSelected()) {
            g.setPaint(Utils.tileFillSelected);
        } else {
            g.setPaint(Utils.tileFill);
        }

        g.fillRoundRect(
                tile.getTileX(),
                tile.getTileY(),
                Utils.getTileSize(),
                Utils.getTileSize(),
                5,
                5
        );

        g.setPaint(Utils.tileBorder);
        g.drawRoundRect(
                tile.getTileX(),
                tile.getTileY(),
                Utils.getTileSize(),
                Utils.getTileSize(),
                5,
                5
        );

        g.drawImage(
                icons.get(tile.getTileType()),
                iconX,
                iconY,
                Utils.getIconSize(),
                Utils.getIconSize(),
                null
        );
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
     * Returns an array to save both the special candy and the normal one.
     * The special candy is always returned inside the first position of the array.
     *
     * @param tilesToValidate - BoardTiles that have been swiped.
     * @return - BoardTile array. Null if there are no special ones.
     */
    private BoardTile[] checkIfSpecialCandyInvolved(BoardTile[] tilesToValidate) {
        if (isSpecialCandy(tilesToValidate[0])) {
            return new BoardTile[]{tilesToValidate[0], tilesToValidate[1]};
        }

        if (isSpecialCandy(tilesToValidate[1])) {
            return new BoardTile[]{tilesToValidate[1], tilesToValidate[0]};
        }

        return null;
    }

    private boolean isSpecialCandy(BoardTile tile) {
        return tile.getTileType() == TileType.POISON_GREEN
                || tile.getTileType() == TileType.POISON_RED
                || tile.getTileType() == TileType.FIREWORK
                || tile.getTileType() == TileType.MUMMY;
    }

    private boolean checkIfFireworksSpecialCandyInvolved(BoardTile specialCandy) {
        if (specialCandy != null) {
            return specialCandy.getTileType() == TileType.FIREWORK;
        } else {
            return false;
        }
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

        BoardTile[] specialCandy = checkIfSpecialCandyInvolved(tilesToValidate);
        boolean valid = false, isFirework = false;
        potentialCrush = new Crush();

        if (specialCandy != null) {
            isFirework = checkIfFireworksSpecialCandyInvolved(specialCandy[0]);
        }

        if (!isFirework) {
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

                        potentialCrush.addCrushedCandy((controller.getTiles().get(row + 2).get(col)));
                        potentialCrush.addCrushedCandy((controller.getTiles().get(row + 1).get(col)));
                        potentialCrush.addCrushedCandy(tile);
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

                        potentialCrush.addCrushedCandy(controller.getTiles().get(row + 1).get(col));
                        potentialCrush.addCrushedCandy(tile);
                        potentialCrush.addCrushedCandy(controller.getTiles().get(row - 1).get(col));
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

                        potentialCrush.addCrushedCandy(tile);
                        potentialCrush.addCrushedCandy(controller.getTiles().get(row - 1).get(col));
                        potentialCrush.addCrushedCandy(controller.getTiles().get(row - 2).get(col));
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

                        potentialCrush.addCrushedCandy(controller.getTiles().get(row).get(col + 2));
                        potentialCrush.addCrushedCandy(controller.getTiles().get(row).get(col + 1));
                        potentialCrush.addCrushedCandy(tile);
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

                        potentialCrush.addCrushedCandy(controller.getTiles().get(row).get(col + 1));
                        potentialCrush.addCrushedCandy(tile);
                        potentialCrush.addCrushedCandy(controller.getTiles().get(row).get(col - 1));
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

                        potentialCrush.addCrushedCandy(tile);
                        potentialCrush.addCrushedCandy(controller.getTiles().get(row).get(col - 1));
                        potentialCrush.addCrushedCandy(controller.getTiles().get(row).get(col - 2));
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
                if (specialCandy != null) {
                    addExtraTilesToExplode(specialCandy);
                }

                manageExplosion();
            }
        } else {
            explodeSameTypeCandies(specialCandy, startTile, endTile, spaceToMove);
            manageExplosion();
        }

    }

    /**
     * Explodes the potential crush and updates
     * the player score, movements, etc.
     */
    private void manageExplosion() {
        CardGameplay.oneMovementLess();
        potentialCrush.explode(controller);
        CardGameplay.updateScore(potentialCrush.getCrushedCandies().size());
    }

    /**
     * Looks for all the candies of the swiped tile through the board
     * and adds them to the potential crush in the board. Only in case
     * the other swiped tile is not a special one too.
     *
     * @param specialCandy - [special candy, candy to crush]
     * @param startTile    - tile that started the swipe
     * @param endTile      - tile that ended the swipe
     * @param spaceToMove  - along the board (tile size)
     */
    private void explodeSameTypeCandies(BoardTile[] specialCandy, BoardTile startTile,
                                        BoardTile endTile, int spaceToMove) {
        if (!isSpecialCandy(specialCandy[1])) {
            TileType toExplode = specialCandy[1].getTileType();
            potentialCrush.addCrushedCandy(specialCandy[0]);

            for (int i = 0; i < controller.getTiles().size(); i++) {
                ArrayList<BoardTile> candyRow = controller.getTiles().get(i);

                for (BoardTile potentialExplosion : candyRow) {
                    if (potentialExplosion.getTileType() == toExplode) {
                        potentialCrush.addCrushedCandy(potentialExplosion);
                    }
                }
            }
        } else {
            swipeTiles(endTile, startTile,
                    endTile.getTileX(), endTile.getTileY(),
                    startTile.getTileX(), startTile.getTileY(),
                    spaceToMove, false);
        }
    }

    /**
     * Adds to the potential crush as many tiles as the type of
     * special candy determines.
     *
     * @param specialCandy - the special candy that has been crushed.
     */
    private void addExtraTilesToExplode(BoardTile[] specialCandy) {
        BoardTile special = specialCandy[0];

        if (special.getTileType() == TileType.POISON_GREEN) {
            int rowToExplode = special.getTileRow();
            ArrayList<BoardTile> extraTilesToExplode = controller.getTiles().get(rowToExplode);
            potentialCrush.addCrushedCandies(extraTilesToExplode);
        }

        if (special.getTileType() == TileType.POISON_RED) {
            int columnToExplode = special.getTileCol();

            for (int i = 0; i < controller.getTiles().size(); i++) {
                ArrayList<BoardTile> row = controller.getTiles().get(i);
                potentialCrush.addCrushedCandy(row.get(columnToExplode));
            }
        }

        if (special.getTileType() == TileType.MUMMY) {
            // Preventing index out of bound
            int minRow = Math.max(special.getTileRow() - 1, 0);
            int maxRow = Math.min(special.getTileRow() + 1, controller.getLevel().getNumRows() - 1);
            int minCol = Math.max(special.getTileCol() - 1, 0);
            int maxCol = Math.min(special.getTileCol() + 1, controller.getLevel().getNumColumns() - 1);

            for (int i = minRow; i <= maxRow; i++) {
                for (int j = minCol; j <= maxCol; j++) {
                    potentialCrush.addCrushedCandy(controller.getTiles().get(i).get(j));
                }
            }
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
                    || controller.getTiles().get(positionX - 2).get(positionY).getTileType() == TileType.EMPTY) {
                return true;
            }

            return type != controller.getTiles().get(positionX - 1).get(positionY).getTileType()
                    || type != controller.getTiles().get(positionX - 2).get(positionY).getTileType();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean notThreeInARowDimensionY(TileType type, ArrayList<BoardTile> row, int positionY) {
        if (row.get(positionY - 1).getTileType() == TileType.EMPTY
                || row.get(positionY - 2).getTileType() == TileType.EMPTY) {
            return true;
        }

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

    /**
     * Checks if there are more automatically
     * generated crushes in the board.
     * At the end of the process, it checks whether
     * the player has won or lost the game.
     */
    public void checkBoard() {
        Crush potentialCrush = new Crush();

        for (int i = 0; i < controller.getTiles().size(); i++) {
            for (int j = 0; j < controller.getTiles().get(i).size(); j++) {
                BoardTile tile = controller.getTiles().get(i).get(j);

                if (i >= 2 && !notThreeInARowDimensionX(tile.getTileType(), i, j)) {
                    potentialCrush.addCrushedCandy(tile);
                    potentialCrush.addCrushedCandy(controller.getTiles().get(i - 1).get(j));
                    potentialCrush.addCrushedCandy(controller.getTiles().get(i - 2).get(j));
                }

                if (j >= 2 && !notThreeInARowDimensionY(tile.getTileType(), controller.getTiles().get(i), j)) {
                    potentialCrush.addCrushedCandy(tile);
                    potentialCrush.addCrushedCandy(controller.getTiles().get(i).get(j - 1));
                    potentialCrush.addCrushedCandy(controller.getTiles().get(i).get(j - 2));
                }
            }
        }

        if (potentialCrush.getCrushedCandies().size() > 0) {
            potentialCrush.explode(controller);
            CardGameplay.updateScore(potentialCrush.getCrushedCandies().size());
        } else {
            controller.getModel().setEnabled(true);
            controller.checkEndOfGame();
        }
    }

    public void removeCandies(ArrayList<BoardTile> crushedCandies) {
        controller.setCrushedCandies(crushedCandies);
        generateSpecialTiles(crushedCandies);

        for (BoardTile tile : crushedCandies) {
            controller.getTiles()
                    .get(tile.getTileRow())
                    .get(tile.getTileCol())
                    .setTileType(TileType.CRUSHED);
        }

        dropCandies();
        controller.repaint();
    }

    /**
     * Slide down animation when generating
     * new tiles in the board.
     */
    public void dropCandies() {
        controller.setEnabled(false);
        int[] crushedInCol = new int[controller.getLevel().getNumColumns()];
        boolean[] colUpdating = new boolean[controller.getLevel().getNumColumns()];
        ArrayList<ArrayList<BoardTile>> tilesToUpdateByCol = new ArrayList<>();

        for (int i = 0; i < controller.getLevel().getNumColumns(); i++) {
            tilesToUpdateByCol.add(new ArrayList<>());
        }

        for (BoardTile candy : controller.getCrushedCandies()) {
            for (int i = candy.getTileRow() - 1; i >= 0; i--) {
                BoardTile tile = controller.getTiles().get(i).get(candy.getTileCol());
                if (tile.getTileType() != TileType.CRUSHED && !tilesToUpdateByCol.get(candy.getTileCol()).contains(tile)) {
                    tilesToUpdateByCol.get(candy.getTileCol()).add(tile);

                }
            }
        }

        for (ArrayList<BoardTile> boardTiles : controller.getTiles()) {
            for (int j = 0; j < boardTiles.size(); j++) {
                if (boardTiles.get(j).getTileType() == TileType.CRUSHED) {
                    crushedInCol[j]++;
                    if (!colUpdating[j]) colUpdating[j] = true;
                }
            }
        }

        ArrayList<ArrayList<BoardTile>> newTiles = generateNewTiles(crushedInCol, tilesToUpdateByCol);
        updateRowsCols(newTiles, tilesToUpdateByCol, controller.getCrushedCandies());

        Timer dropTimer = new Timer(0, e -> {
            boolean updating = false;

            for (ArrayList<BoardTile> column : tilesToUpdateByCol) {
                for (BoardTile tile : column) {
                    if (tile.getTileRow() * Utils.getTileSize() > tile.getTileY()) {
                        tile.setTileY(tile.getTileY() + Utils.getTileSize() / 10);
                    }
                    if (colUpdating[tile.getTileCol()] && controller.getTiles().get(0).get(tile.getTileCol()).getTileY() == 0) {
                        colUpdating[tile.getTileCol()] = false;
                    }
                }
            }

            controller.repaint();

            for (boolean colUpdate : colUpdating) {
                if (colUpdate) {
                    updating = true;
                    break;
                }
            }

            if (!updating) {
                ((Timer) e.getSource()).stop();
                checkBoard();
            }
        });

        dropTimer.setRepeats(true);
        dropTimer.setInitialDelay(0);
        dropTimer.start();
    }

    private void updateRowsCols(ArrayList<ArrayList<BoardTile>> newTiles,
                                ArrayList<ArrayList<BoardTile>> tilesToUpdateByCol,
                                ArrayList<BoardTile> crushedCandies) {

        for (int i = 0; i < tilesToUpdateByCol.size(); i++) {
            for (int j = 0; j < tilesToUpdateByCol.get(i).size(); j++) {
                BoardTile tileToUpdate = tilesToUpdateByCol.get(i).get(j);

                if (tileToUpdate.getTileY() >= 0) {
                    int rowShift = 0;

                    for (BoardTile crushedCandy : crushedCandies) {
                        if (crushedCandy.getTileCol() == i && crushedCandy.getTileRow() > tileToUpdate.getTileRow()) {
                            rowShift++;
                        }
                    }

                    tileToUpdate.setTileRow(tileToUpdate.getTileRow() + rowShift);

                    try {
                        controller.getTiles().get(tileToUpdate.getTileRow()).set(i, tileToUpdate);
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        for (ArrayList<BoardTile> col : newTiles) {
            for (BoardTile tile : col) {
                controller.getTiles().get(tile.getTileRow()).set(tile.getTileCol(), tile);
            }
        }

        setSpecialTypes();
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

        for (int i = 0; i < controller.getLevel().getNumColumns(); i++) {
            newTiles.add(new ArrayList<>());

            for (int j = 1; j <= crushedInCol[i]; j++) {
                TileType type = types[random.nextInt(5)];

                BoardTile newTile = new BoardTile(
                        type,
                        j - 1,
                        i,
                        i * Utils.getTileSize(),
                        -(crushedInCol[i] - j + 1) * Utils.getTileSize()
                );

                newTiles.get(i).add(newTile);
                tilesToUpdate.get(i).add(newTile);
            }
        }

        return newTiles;
    }

    private void generateSpecialTiles(ArrayList<BoardTile> crushedCandies) {
        // Row
        for (int i = 0; i < controller.getLevel().getNumRows(); i++) {
            ArrayList<Integer> orderedRowCrush = new ArrayList<>();

            for (BoardTile candy : crushedCandies) {
                if (candy.getTileRow() == i) {
                    orderedRowCrush.add(candy.getTileCol());
                }
            }

            // Order the array now
            Collections.sort(orderedRowCrush);

            if (orderedRowCrush.size() >= 3) {
                for (int j = 0; j < orderedRowCrush.size(); j++) {
                    int col = orderedRowCrush.get(j);

                    if (mummyCheck(controller.getTiles().get(i).get(col), crushedCandies)) {
                        specialCoordinates.add(new int[]{i, col});
                        specialTypes.add(TileType.MUMMY);
                        orderedRowCrush.remove(j);

                        j--;
                    }
                }
            }

            for (int j = 3; j < orderedRowCrush.size(); j++) {
                BoardTile thisTile = controller.getTiles().get(i).get(orderedRowCrush.get(j));
                BoardTile tileOneBack = controller.getTiles().get(i).get(orderedRowCrush.get(j - 1));
                BoardTile tileTwoBack = controller.getTiles().get(i).get(orderedRowCrush.get(j - 2));
                BoardTile tileThreeBack = controller.getTiles().get(i).get(orderedRowCrush.get(j - 3));
                BoardTile nextTile;

                if (j + 1 < orderedRowCrush.size()) {
                    nextTile = controller.getTiles().get(i).get(orderedRowCrush.get(j + 1));
                } else {
                    nextTile = null;
                }

                if (thisTile.getTileCol() == tileThreeBack.getTileCol() + 3
                        && thisTile.getTileType() == tileOneBack.getTileType()
                        && thisTile.getTileType() == tileTwoBack.getTileType()
                        && thisTile.getTileType() == tileThreeBack.getTileType()) {

                    if (nextTile != null
                            && thisTile.getTileCol() == nextTile.getTileCol() - 1
                            && thisTile.getTileType() == nextTile.getTileType()) {
                        specialCoordinates.add(new int[]{nextTile.getTileRow(), nextTile.getTileCol()});
                        specialTypes.add(TileType.FIREWORK);
                        orderedRowCrush.remove(j + 1);
                    } else {
                        specialCoordinates.add(new int[]{thisTile.getTileRow(), thisTile.getTileCol()});
                        specialTypes.add(TileType.POISON_GREEN);
                    }

                    orderedRowCrush.remove(j);
                    orderedRowCrush.remove(j - 1);
                    orderedRowCrush.remove(j - 2);
                    orderedRowCrush.remove(j - 3);

                    j = 2;
                }
            }
        }

        // Column
        for (int i = 0; i < controller.getLevel().getNumColumns(); i++) {
            ArrayList<Integer> orderedColCrush = new ArrayList<>();

            for (BoardTile candy : crushedCandies) {
                if (candy.getTileCol() == i) {
                    orderedColCrush.add(candy.getTileRow());
                }
            }

            // Order the array now
            Collections.sort(orderedColCrush);

            for (int j = 3; j < orderedColCrush.size(); j++) {
                BoardTile thisTile = controller.getTiles().get(orderedColCrush.get(j)).get(i);
                BoardTile tileOneBack = controller.getTiles().get(orderedColCrush.get(j - 1)).get(i);
                BoardTile tileTwoBack = controller.getTiles().get(orderedColCrush.get(j - 2)).get(i);
                BoardTile tileThreeBack = controller.getTiles().get(orderedColCrush.get(j - 3)).get(i);
                BoardTile nextTile;

                if (j + 1 < orderedColCrush.size()) {
                    nextTile = controller.getTiles().get(orderedColCrush.get(j + 1)).get(i);
                } else {
                    nextTile = null;
                }

                if (thisTile.getTileRow() == tileThreeBack.getTileRow() + 3
                        && thisTile.getTileType() == tileOneBack.getTileType()
                        && thisTile.getTileType() == tileTwoBack.getTileType()
                        && thisTile.getTileType() == tileThreeBack.getTileType()) {

                    if (nextTile != null
                            && thisTile.getTileRow() == nextTile.getTileRow() - 1
                            && thisTile.getTileType() == nextTile.getTileType()) {
                        specialCoordinates.add(new int[]{nextTile.getTileRow(), nextTile.getTileCol()});
                        specialTypes.add(TileType.FIREWORK);
                        orderedColCrush.remove(j + 1);
                    } else {
                        specialCoordinates.add(new int[]{thisTile.getTileRow(), thisTile.getTileCol()});
                        specialTypes.add(TileType.POISON_RED);
                    }

                    orderedColCrush.remove(j);
                    orderedColCrush.remove(j - 1);
                    orderedColCrush.remove(j - 2);
                    orderedColCrush.remove(j - 3);

                    j = 2;
                }
            }
        }
    }

    private void setSpecialTypes() {
        for (int i = 0; i < specialCoordinates.size(); i++) {
            controller.getTiles()
                    .get(specialCoordinates.get(i)[0])
                    .get(specialCoordinates.get(i)[1])
                    .setTileType(specialTypes.get(i));
        }

        specialCoordinates = new ArrayList<>();
        specialTypes = new ArrayList<>();
    }

    private boolean mummyCheck(BoardTile tile, ArrayList<BoardTile> crushedTiles) {
        return (tile.getTileRow() <= controller.getLevel().getNumRows() - 3 &&
                crushedTiles.contains(controller.getTiles().get(tile.getTileRow() + 1).get(tile.getTileCol()))
                && crushedTiles.contains(controller.getTiles().get(tile.getTileRow() + 2).get(tile.getTileCol()))
                && controller.getTiles().get(tile.getTileRow() + 1).get(tile.getTileCol()).getTileType() == tile.getTileType()
                && controller.getTiles().get(tile.getTileRow() + 2).get(tile.getTileCol()).getTileType() == tile.getTileType())
                || (tile.getTileRow() >= 2 &&
                crushedTiles.contains(controller.getTiles().get(tile.getTileRow() - 1).get(tile.getTileCol()))
                && crushedTiles.contains(controller.getTiles().get(tile.getTileRow() - 2).get(tile.getTileCol()))
                && controller.getTiles().get(tile.getTileRow() - 1).get(tile.getTileCol()).getTileType() == tile.getTileType()
                && controller.getTiles().get(tile.getTileRow() - 2).get(tile.getTileCol()).getTileType() == tile.getTileType());
    }
}

