package model.tiles.units.players;

import  model.game.Board;
import model.tiles.Tile;
import model.tiles.units.enemies.Enemy;
import utils.generators.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

public class Warrior extends Player {
    private final int cooldown;
    private int remainingCooldown;
    private final int ABILITY_RANGE = 3;
    private final int LEVELUP_HEALTH = 5;
    private final int LEVELUP_ATTACK = 2;
    private final int LEVELUP_DEFENSE = 1;
    private final int ABILITY_DEFENSE = 10;
    private final double ABILITY_HIT = 0.1;



    public Warrior(String name, int hitPoints, int attack, int defense,int cooldown) {
        super(name, hitPoints, attack, defense);
        this.cooldown=cooldown;
        this.remainingCooldown=0;
    }

    @Override
    public void levelUp() {
        super.levelUp();
        this.remainingCooldown=0;
        int healthGain=this.level*LEVELUP_HEALTH;
        int attackGain=LEVELUP_ATTACK * this.level;
        int defenseGain =  LEVELUP_DEFENSE * this.level;;

        this.health.increaseMax(healthGain);
        this.attack += attackGain;
        this.defense += defenseGain;
    }

    //need to take argument?
    public void onTick(Tile tile)
    {
        super.onTick(tile);
        this.remainingCooldown=Math.min(0,this.remainingCooldown-1);
    }

    //enemies list from board! change argument?
    public void castAbility(List<Enemy> enemies)
    {
        /*
        remaining cooldown ← ability cooldown
        - current health ← min (current health + (10 × defense), health pool)
         Randomly hits one enemy within range < 3 for an amount equals to 10% of the warrior’s
           health pool
         */
        this.remainingCooldown=this.cooldown;
        int cap=this.health.getCurrent()+ABILITY_DEFENSE*this.defense;
        int current=Math.min(cap,this.health.getCapacity());
        this.health.newCurrent(current);
        //randomly hits enemy
        List<Enemy> enemiesRange=enemiesInRange(enemies,3);
        if(enemiesRange.size()>0)
        {
            //RandomGenerator randomGenerator = new RandomGenerator();
            int randomValue = this.generator.generate(enemiesRange.size());
            Enemy e=enemiesRange.get(randomValue);
            int attack=(int)ABILITY_HIT*this.health.getCapacity();
            this.visit(e,attack);
        }

    }
    //ABILITY_RANGE
}
