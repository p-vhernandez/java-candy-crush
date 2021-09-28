package UI;

import Components.BoardGrid;
import Components.BoardTile;
import Helpers.Colors;
import Helpers.TileType;
import Model.BoardTileModel;
import utils.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.Timer;

public class BoardTileUI {

    public void installUI(BoardTile tile) {
        BoardTileModel model = tile.getModel();
        tile.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //super.mousePressed(e);
                model.setPressed(true);
                ((BoardGrid)tile.getParent()).setTileDragStart((BoardTile)e.getSource());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                model.setPressed(false);

            }
        });

        tile.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                BoardTile endTile = getTile(((BoardGrid)tile.getParent()).getTiles(), e.getX(), e.getY());
                BoardTile startTile = ((BoardGrid)tile.getParent()).getTileDragStart();
                int endCol = endTile.getTileCol();
                int endRow = endTile.getTileRow();
                int startCol = startTile.getTileCol();
                int startRow = startTile.getTileRow();
                if ((Math.abs(startCol-endCol) == 1 && startRow == endRow)|| (Math.abs(startRow-endRow) == 1) && startCol == endCol) {
                    //TO DO: swipe conditions
                    //swipeTiles(startTile, endTile);
                }
            }
        });
    }

    public void paint(Graphics2D g, BoardTile tile) {
        int iconX = tile.getTileX() + (Utils.getTileSize() - Utils.getIconSize())/2;
        int iconY = tile.getTileY() + (Utils.getTileSize() - Utils.getIconSize())/2;
        BufferedImage icon = null;
        try {
            icon = ImageIO.read(getClass().getResourceAsStream("../resources/img/" + tile.getTileType() +".png"));
        } catch (IOException e){
            e.printStackTrace();
        }
        g.setPaint(Colors.tileFill);
        g.fillRoundRect(tile.getTileX(), tile.getTileY(), Utils.getTileSize(), Utils.getTileSize(), 5,5);
        g.setPaint(Colors.tileBorder);
        g.drawRoundRect(tile.getTileX(), tile.getTileY(), Utils.getTileSize(), Utils.getTileSize(), 5,5);
        g.drawImage(icon, iconX, iconY, Utils.getIconSize(), Utils.getIconSize(), null);
    }

    private BoardTile getTile(BoardTile[][] tiles, int x, int y) {
        int tileSize = Utils.getTileSize();
        int col = (int)Math.floor(x / tileSize);
        int row = (int)Math.floor(y/tileSize);
        return tiles[row][col];
    }

    private void swipeTiles(BoardTile startTile, BoardTile endTile) {
        Timer myTimer = new Timer(Utils.getAnimSlideVelocity(), e -> {

            if (startTile.getTileCol() < endTile.getTileCol()) {

                //System.out.println("start: " +startTile.getTileX());
                //System.out.println("end: " +endTile.getTileX());

                startTile.setTileX(startTile.getTileX()+1);
                //endTile.setTileX(endTile.getTileX()-1);

                if (startTile.getTileX() == Utils.getTileSize()/2 && endTile.getTileX() == -Utils.getTileSize()/2) {
                    ((Timer)e.getSource()).stop();
                    //System.out.println(-Utils.getTileSize());
                    //System.out.println("end: " +endTile.getTileX());
                    BoardTile [][] tiles = ((BoardGrid)startTile.getParent()).getTiles();
                    startTile.setTileCol(endTile.getTileCol());
                    endTile.setTileCol(startTile.getTileCol());
                    startTile.setTileX(0);
                    endTile.setTileX(0);
                    tiles[startTile.getTileRow()][startTile.getTileCol()] = endTile;
                    tiles[endTile.getTileRow()][endTile.getTileCol()] = startTile;
                    ((BoardGrid)startTile.getParent()).setTiles(tiles);
                }
            } else if (startTile.getTileCol() > endTile.getTileCol()) {
                startTile.setTileX(startTile.getTileX() - 1);
                endTile.setTileX(endTile.getTileX() + 1);
            }
        });

        myTimer.start();
    }
}
