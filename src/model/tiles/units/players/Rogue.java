package model.tiles.units.players;

import model.tiles.Tile;
import model.tiles.units.enemies.Enemy;
import utils.boardController;

import java.util.List;

public class Rogue extends Player{ //not done
    private int cost;
    private int Energy;
    private static final int range = 2;
    private static final int ENERGY_MAX = 100;
    public Rogue(String name, int hitPoints, int attack, int defense,int cost) {
        super(name, hitPoints, attack, defense);
        this.abilityName ="Fan of Knives";
        this.cost=cost;
        this.refillEnergy();
    }

    @Override
    public void levelUp() {
        super.levelUp();
        attack = attack + 3 * level;
        this.refillEnergy();
    }
    public void refillEnergy(){
        this.Energy=ENERGY_MAX;
    }
    public void onTick(Tile tile){
        super.onTick(tile);
        this.Energy = Math.min(this.Energy+10,ENERGY_MAX);
    }
    public void castAbility() {
        if (this.Energy < this.cost) {
            messageCallback.send("Not enough energy for Fan of Knives");
            //return "Not enough energy for Fan of Knives";
        }
        else {
        this.Energy = this.Energy - this.cost;
        List<Enemy> enemyList = boardController.enemiesInRange(this, range);
        for(Enemy e:enemyList)
        {
            int defense = e.defend();
            int attack = this.attack;
            int damage = attack-defense;
            if (damage > 0) {
                //not sure about this argument
                e.takeDamage(damage);
            } else {
                e.onDeath();
            }
        }
        messageCallback.send("Fan of Knives casted, dealing " + this.attack + " damage to all enemies");
        //return "Fan of Knives casted, dealing " + this.attack + " damage to all enemies";
    }}
}
