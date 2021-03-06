package utils.helpers;

import utils.BoardTile;
import components.grid.BoardGrid;

import javax.swing.*;
import java.util.ArrayList;

public class Crush {

    private ArrayList<BoardTile> crushedCandies;
    private ArrayList<Explosion> explosions;

    public Crush() {
        this.crushedCandies = new ArrayList<>();
        this.explosions = new ArrayList<>();
    }

    public Crush(ArrayList<BoardTile> crushedCandies) {
        this.crushedCandies = crushedCandies;
    }

    public void setCrushedCandies(ArrayList<BoardTile> crushedCandies) {
        this.crushedCandies = crushedCandies;
    }

    public void addCrushedCandy(BoardTile crushedCandy) {
        if (!crushedCandies.contains(crushedCandy)) {
            this.crushedCandies.add(crushedCandy);
            addExplosion(
                    new Explosion(
                            crushedCandy.getTileX(),
                            crushedCandy.getTileY(),
                            crushedCandy.getPreferredSize().width / 8,
                            30)
            );
        }
    }

    public void addCrushedCandies(ArrayList<BoardTile> crushedCandies) {
        for (BoardTile tile : crushedCandies) {
            addCrushedCandy(tile);
        }
    }

    public ArrayList<BoardTile> getCrushedCandies() {
        return this.crushedCandies;
    }

    public ArrayList<Explosion> getExplosions() {
        return this.explosions;
    }

    public void addExplosion(Explosion explosion) {
        this.explosions.add(explosion);
    }

    public void explode(BoardGrid grid) {
        Timer explosionTimer = new Timer(5, e -> {
            for (int i = 0; i < explosions.size(); i++) {
                boolean remove = explosions.get(i).update();

                if (remove) {
                    explosions.remove(explosions.get(i));
                    if (explosions.size() == 0) {
                        grid.removeCandies(crushedCandies);
                        i = explosions.size();
                        ((Timer) e.getSource()).stop();
                    }
                }
            }

            grid.repaint();
        });

        explosionTimer.setRepeats(true);
        explosionTimer.start();
    }

}
