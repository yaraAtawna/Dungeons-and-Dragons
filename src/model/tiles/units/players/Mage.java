package model.tiles.units.players;

import model.tiles.units.enemies.Enemy;
import utils.Position;

import java.util.Random;

public class Mage extends  Player{
   // protected Position position;
    private int manaPool;
    private int mana_cost;
    private int spellPower;
    private int hitCount ;
    private int range;
    private int current_mana;

    public Mage(String name, int hitPoints, int attack, int defense,int manaPool,int manaCost,int spellPower,int hitCount ,int range) {
        super(name, hitPoints, attack, defense);
        this.abilityName ="Blizzard";
        this.hitCount=hitCount;
        this.manaPool=manaPool;
        this.mana_cost=manaCost;
        this.spellPower=spellPower;
        this.range=range;
        this.current_mana=manaPool/4;

    }

    @Override
    public void levelUp() {
        super.levelUp();
        this.manaPool=this.manaPool+ (25*this.level);
        this.current_mana=Math.min(this.current_mana+this.manaPool/4,this.manaPool);
        this.spellPower=this.spellPower+10*this.level;
    }
    public String castAbility() {
        if (this.current_mana < this.mana_cost) {
            return "Not enough mana for Blizzard";
        }
        this.current_mana = this.current_mana - this.mana_cost;
        Random rand = new Random();
        int range = this.spellPower;
        int hits = 0;
        //the code inside the while is not complete
        //a code to randomly hit the enemys in the range while the hits is smaller than hitCount
        while (hits < hitCount /*&& thereIsAlliveEnemyInRange(range)*/) {
            hits++;



        }
        return "Blizzard casted, dealing " + this.spellPower + " damage to all enemies in range";
    }

}
