package model.tiles.units.players;

import model.tiles.units.enemies.Enemy;

import java.util.List;

public class Rogue extends Player{
    private int cost;
    private int Energy;
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
    public void GameTick(){
        this.Energy = Math.min(this.Energy+10,ENERGY_MAX);
    }
    public String castAbility() {
        if (this.Energy < this.cost) {
            return "Not enough energy for Fan of Knives";
        }
        this.Energy = this.Energy - this.cost;
        /*List<Enemy> enemies = this.position.getEnemiesInRange(2);
        for(Enemy e:enemies){
            this.visit(e);
        }*/
        return "Fan of Knives casted, dealing " + this.attack + " damage to all enemies";
    }
}
