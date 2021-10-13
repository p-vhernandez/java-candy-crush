package utils.helpers;

import utils.Utils;

import java.awt.*;

/**
 * Found and adapted from YouTube video:
 * https://www.youtube.com/watch?v=U7y5lKgbvGE&t=433s&ab_channel=ForeignGuyMike
 */
public class Explosion {

    private final int tileOriginX, tileOriginY;
    private final int maxRadius;

    private double x, y;
    private int radius;

    private boolean even = false;

    public Explosion(int tileOriginX, int tileOriginY,
                     int radius, int maxRadius) {
        this.tileOriginX = tileOriginX;
        this.tileOriginY = tileOriginY;
        this.radius = radius;
        this.maxRadius = maxRadius;

        calculateXandY();
    }

    public boolean update() {
        radius += 5;
        return radius >= maxRadius;
    }

    public void draw(Graphics2D graphics2D) {
        if (even) {
            graphics2D.setColor(new Color(93, 201, 122, 180));
            graphics2D.setStroke(new BasicStroke(4));
        } else {
            graphics2D.setColor(new Color(13, 184, 59));
            graphics2D.setStroke(new BasicStroke(2));
        }

        even = !even;
        graphics2D.drawOval((int) (x - radius), (int) (y - radius),
                2 * radius, 2 * radius);
    }

    private void calculateXandY() {
        x = tileOriginX + (Utils.getTileSize() / 2);
        y = tileOriginY + (Utils.getTileSize() / 2);
    }

    @Override
    public String toString() {
        return "Explosion{" +
                "tileOriginX=" + tileOriginX +
                ", tileOriginY=" + tileOriginY +
                ", maxRadius=" + maxRadius +
                ", x=" + x +
                ", y=" + y +
                ", radius=" + radius +
                '}';
    }
}
