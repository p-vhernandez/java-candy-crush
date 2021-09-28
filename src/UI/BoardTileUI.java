package UI;

import Components.BoardGrid;
import Components.BoardTile;

import utils.Utils;
import Model.BoardTileModel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
                //super.mouseReleased(e);
                model.setPressed(false);
                System.out.println(((BoardTile)e.getSource()).getTileType());
            }
        });

        tile.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                //this should go to mouse released -- then the drag method is unnecessary
                BoardTile tileDragStart = ((BoardGrid)tile.getParent()).getTileDragStart();
                //if ((e.getSource())
            }
        });
    }

    public void paint(Graphics2D g, BoardTile tile) {
        int iconXY = (tile.getTileSize() - tile.getIconSize())/2;
        BufferedImage icon = null;
        try {
            icon = ImageIO.read(getClass().getResourceAsStream("../resources/img/" + tile.getTileType() +".png"));
        } catch (IOException e){
            e.printStackTrace();
        }
        g.setPaint(Utils.tileFill);
        g.fillRoundRect(0,0, tile.getTileSize(), tile.getTileSize(), 5,5);
        g.setPaint(Utils.tileBorder);
        g.drawRoundRect(0,0, tile.getTileSize(), tile.getTileSize(), 5,5);
        g.drawImage(icon, iconXY, iconXY, tile.getIconSize(), tile.getIconSize(), null);
    }

    private BoardTile getTile(BoardTile[][] tiles, int x, int y) {
        int tileSize = tiles[0][0].getTileSize();
        int row = (int)Math.floor(x / tileSize);
        int col = (int)Math.floor(y/tileSize);
        System.out.println(row);
        System.out.println(col);
        return tiles[row][col];
    }
}
