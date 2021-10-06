package utils.helpers;

import components.BoardTile;

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

    public void addCrushedCandies(BoardTile crushedCandy) {
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

    public ArrayList<BoardTile> getCrushedCandies() {
        return this.crushedCandies;
    }

    public ArrayList<Explosion> getExplosions() {
        return this.explosions;
    }

    public void addExplosion(Explosion explosion) {
        this.explosions.add(explosion);
    }

    public void crushCandies() {
        for (BoardTile tile : crushedCandies) {
            tile.setTileType(TileType.MUMMY);
        }

        for (int i = 0; i < explosions.size(); i++) {
            boolean remove = explosions.get(i).update();

            if (remove) {
                explosions.remove(explosions.get(i));
                i--;
            }
        }
    }

}
