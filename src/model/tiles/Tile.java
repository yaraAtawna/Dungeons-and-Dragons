package model.tiles;

import model.tiles.units.Unit;
import utils.Position;
import utils.boardController;

public abstract class Tile {
    protected char tile;
    protected Position position;

    public Tile(char tile){
        this.tile = tile;
    }

    public Tile initialize(Position p){
        this.position = p;
        return this;
    }

    //swap with empty tile
    public void swapPosition(Tile t,Position p) {
        boardController.swapPosEmpty(this, p);

    }

    @Override
    public String toString() {
        return String.valueOf(tile);
    }

    public abstract void accept(Unit unit);

    public Position getPosition() {
        return position;
    }
    public Tile setTile(char c)
    {
        this.tile = c;
        return this;
    }
}
