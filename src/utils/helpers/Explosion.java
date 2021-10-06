package utils.helpers;

import utils.Utils;

import java.awt.*;

/**
 * https://www.youtube.com/watch?v=U7y5lKgbvGE&t=433s&ab_channel=ForeignGuyMike
 */
public class Explosion {

    private final int tileOriginX, tileOriginY;
    private final int maxRadius;

    private double x, y;
    private int radius;

    // TODO: combine with actual play
    public Explosion(int tileOriginX, int tileOriginY,
                     int radius, int maxRadius) {
        this.tileOriginX = tileOriginX;
        this.tileOriginY = tileOriginY;
        this.radius = radius;
        this.maxRadius = maxRadius;

        calculateXandY();
    }

    public boolean update() {
        radius++;
        return radius >= maxRadius;
    }

    public void draw(Graphics2D graphics2D) {
        // TODO: set better color
        graphics2D.setColor(Color.red);
        graphics2D.setStroke(new BasicStroke(2));
        graphics2D.drawOval((int) (x - radius), (int) (y - radius),
                2 * radius, 2 * radius);
    }

    public int getTileOriginX() {
        return tileOriginX;
    }

    public int getTileOriginY() {
        return tileOriginY;
    }

    public int getMaxRadius() {
        return maxRadius;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
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
