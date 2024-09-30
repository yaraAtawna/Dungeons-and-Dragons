package model.tiles.units.enemies;

import model.game.Board;
import model.tiles.Empty;
import model.tiles.Tile;
import model.tiles.units.players.Player;
import utils.boardController;

public class Trap extends Enemy{
    private int VisibilityTime;
    private int InvisibilityTime;
    private int ticksCounter;
    private boolean isVisible;
    private final int range = 2;
    private boolean removed;
    private  char TrapTile;


    public Trap(char tile, String name, int hitPoints, int attack, int defense, int experienceValue,int VisibilityTime,int InvisibilityTime) {
        super(tile, name, hitPoints, attack, defense, experienceValue);
        this.VisibilityTime=VisibilityTime;
        this.InvisibilityTime=InvisibilityTime;
        this.ticksCounter=0;
        this.isVisible=true;
        this.removed=false;
        this.TrapTile=tile;

    }
    public void onTick( Player p)
    {
        isVisible = ticksCounter < VisibilityTime;
        //updated -1
        if(ticksCounter == VisibilityTime + InvisibilityTime-1)
        {
            this.removed=false;
            this.tile=TrapTile;
            ticksCounter = 0;
        }
        else
        {
            ticksCounter++;
        }

        if(!isVisible)
        {
            if(!removed)
            {
                this.tile='.';
                removed=true;

            }
        }
        else
        {
            this.removed=false;
            this.tile=TrapTile;
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
        return String.format(this.name + "-      health: " + this.health.getCurrent()+"/"+this.health.getCapacity()+"  attack: "+ this.attack + "  defense: " + this.defense + "  experience: " + this.experienceValue );
    }
}
