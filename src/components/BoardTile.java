package components;

import utils.helpers.TileType;
import utils.Utils;

import java.awt.*;

public class BoardTile {

    private TileType tileType;
    private int tileRow, tileCol;
    private int tileX, tileY;

    private boolean selected;

    public BoardTile(TileType tileType, int tileRow, int tileCol, int tileX, int tileY) {
        this.tileType = tileType;
        this.tileRow = tileRow;
        this.tileCol = tileCol;

        this.tileX = tileX;
        this.tileY = tileY;

        this.selected = false;
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public int getTileRow() {
        return tileRow;
    }

    public void setTileRow(int tileRow) {
        this.tileRow = tileRow;
    }

    public int getTileCol() {
        return tileCol;
    }

    public void setTileCol(int tileCol) {
        this.tileCol = tileCol;
    }

    public int getTileX() {
        return tileX;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    //Layout
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    public Dimension getPreferredSize() {
        return new Dimension(Utils.getTileSize(), Utils.getTileSize());
    }
}
