package UI;

import Components.BoardGrid;
import Components.BoardTile;
import Helpers.TileType;
import utils.Utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class BoardGridUI {

    public void initializeUI(BoardGrid grid) {
        grid.setBackground(Utils.darkBackground);
    }

    //generates the tiles on the board
    public void generateTiles(int tilesXAxis, int tilesYAxis, BoardGrid grid) {
        BoardTile[][] tiles = grid.getTiles();
        BoardTile[] row;
        Random random = new Random();
        TileType[] types = {TileType.PURPLE_LOLLI, TileType.ORANGE_CANDY, TileType.YELLOW_LOLLI, TileType.EYEBALL, TileType.PUMPKIN};
        for (int i = 0; i < tilesXAxis; i++) {
            row = new BoardTile[tilesXAxis];
            for (int j = 0; j < tilesYAxis; j++) {
                TileType type = types[random.nextInt(5)];
                boolean validX = true;
                boolean validY = true;
                if (i >= 2) { validX = false; }
                if (j >= 2) { validY = false; }
                while (!validX) {
                    if (type != tiles[i-1][j].getTileType() || type != tiles[i-2][j].getTileType()) {
                        validX = true;
                    } else {
                        type = types[random.nextInt(5)];
                    }
                }
                while (!validY) {
                    if (type != row[j-1].getTileType() || type != row[j-2].getTileType()) {
                        validY = true;
                    } else {
                        type = types[random.nextInt(5)];
                    }
                }
                row[j] = new BoardTile(type, i, j);
            }
            tiles[i] = row;
        }
        grid.setTiles(tiles);
    }
}
