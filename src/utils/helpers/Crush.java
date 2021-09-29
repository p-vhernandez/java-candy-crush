package utils.helpers;

import components.BoardTile;

import java.util.ArrayList;

public class Crush {

    private ArrayList<BoardTile> crushedCandies;

    public Crush() {

    }

    public Crush(ArrayList<BoardTile> crushedCandies) {
        this.crushedCandies = crushedCandies;
    }

    public void setCrushedCandies(ArrayList<BoardTile> crushedCandies) {
        this.crushedCandies = crushedCandies;
    }

    public void addCrushedCandies(BoardTile crushedCandy) {
        this.crushedCandies.add(crushedCandy);
    }

    public ArrayList<BoardTile> getCrushedCandies() {
        return this.crushedCandies;
    }

}
