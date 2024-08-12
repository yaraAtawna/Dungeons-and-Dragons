package model.tiles.units.players;

import model.tiles.Tile;
import model.tiles.units.enemies.Enemy;
import utils.Position;
import utils.*  ;

import java.util.List;
import java.util.Random;

public class Mage extends  Player{
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
    public void castAbility() {
        if (this.current_mana < this.mana_cost) {
            messageCallback.send("Not enough mana for Blizzard");
            //return "Not enough mana for Blizzard";//delete this
        }
        else {
        this.current_mana = this.current_mana - this.mana_cost;
        Random rand = new Random();
        int range = this.spellPower;
        int hits = 0;

        //a code to randomly hit the enemys in the range while the hits is smaller than hitCount
        List<Enemy> enemyList = boardController.enemiesInRange(this, range);
        while (hits < hitCount && enemyList.size() > 0) {
            hits++;
            int randomValue = this.generator.generate(enemyList.size());
            Enemy e = enemyList.get(randomValue);

            int defense = e.defend();
            int attack = spellPower;
            int damage = defense - attack;
            if (damage > 0) {
                //not sure about this argument
                e.takeDamage(spellPower);
            } else {
                e.onDeath();
            }
            hits++;
        }
        messageCallback.send("Blizzard casted, dealing " + this.spellPower + " damage to all enemies in range");
        //return "Blizzard casted, dealing " + this.spellPower + " damage to all enemies in range";
    } }
    public void onTick(Tile tile) {
        super.onTick(tile);
        //not sure
        this.current_mana=Math.min(this.current_mana*level,this.manaPool);

    }

}
