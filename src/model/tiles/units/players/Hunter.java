package model.tiles.units.players;

import model.tiles.Tile;
import model.tiles.units.enemies.Enemy;
import utils.boardController;

import java.util.List;

public class Hunter extends Player{
    private int range;
    private int arrowsCount;
    private int ticksCount;
    public Hunter(String name, int hitPoints, int attack, int defense,int range) {
        super(name, hitPoints, attack, defense);
        this.abilityName ="Shoot";
        this.range=range;
        this.arrowsCount=10*this.level;
        this.ticksCount=0;
    }
    public void levelUp() {
        super.levelUp();
        this.arrowsCount=this.arrowsCount+10*this.level;
        this.attack=this.attack+2*this.level;
        this.defense = this.defense+1*this.level;
    }
    @Override
    public String description() {
        return String.format(this.name + "-      health: " + this.health.getCurrent()+"/"+this.health.getCapacity()+"  attack: "+ this.attack + "  defense: " + this.defense + "  level: " + this.level + "  experience: " + this.experience + "/" + this.levelRequirement() +"   arrows : " + this.arrowsCount+"/" + 10*this.level+"   range: "+this.range);
    }

    @Override
    public void castAbility() {
        List<Enemy> enemyList = boardController.enemiesInRange(this, this.range);
        if(arrowsCount==0 || enemyList.size()==0){
            return;
        }
        arrowsCount = arrowsCount - 1;
        double Range = this.range;
        Enemy e = enemyList.get(0);
        for (Enemy enemy:enemyList) {
            if(enemy.getPosition().range(this.position)<Range){
                Range = enemy.getPosition().range(this.position);
                e = enemy;
            }
        }
        this.visit(e);


    }
    public void onTick(Tile tile)
    {
        super.onTick(tile);
        if(ticksCount == 10) {
            arrowsCount = arrowsCount + this.level;
            ticksCount = 0;
        }
        else{
            ticksCount++;
        }


    }

}