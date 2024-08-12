package model.tiles.units.enemies;

import model.tiles.Tile;
import model.tiles.units.players.Player;

public class Trap extends Enemy{
    private int VisibilityTime;
    private int InvisibilityTime;
    private int ticksCounter;
    private boolean isVisible;
    private final int range = 2;
    public Trap(char tile, String name, int hitPoints, int attack, int defense, int experienceValue,int VisibilityTime,int InvisibilityTime) {
        super(tile, name, hitPoints, attack, defense, experienceValue);
        this.VisibilityTime=VisibilityTime;
        this.InvisibilityTime=InvisibilityTime;
        this.ticksCounter=0;
        this.isVisible=true;
    }
    public void onTick( Player p)
    {

        isVisible = ticksCounter < VisibilityTime;
        if(ticksCounter == VisibilityTime + InvisibilityTime)
        {
            ticksCounter = 0;
        }
        else
        {
            ticksCounter++;
        }
        if (this.position.range(p.getPosition()) < range)
        {
            this.battle(p);
        }
    }
    public void attack(Player p){

        this.visit(p);
    }

    @Override
    public String description() {
        return null;
    }
}
