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
        }
    }

    public ArrayList<BoardTile> getCrushedCandies() {
        return this.crushedCandies;
    }

    public void crushCandies() {
        for (BoardTile tile : crushedCandies) {
            tile.setTileType(TileType.MUMMY);
        }
    }

}
