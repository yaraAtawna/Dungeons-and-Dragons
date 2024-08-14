package model.tiles;

import model.tiles.units.Unit;

public class Empty extends Tile {
    public static final char EMPTY_TILE = '.';

    public Empty() {
        super(EMPTY_TILE);
    }

    @Override
    public void accept(Unit unit) {
//        System.out.println("Empty.accept");
//        System.out.println("position is " + this.position.getX() + ", " + this.position.getY());
        unit.visit(this);
    }
}
