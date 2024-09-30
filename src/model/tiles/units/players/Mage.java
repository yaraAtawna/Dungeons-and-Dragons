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

        }
        else {
            this.current_mana = this.current_mana - this.mana_cost;

            int range = this.spellPower;
            int hits = 0;


            List<Enemy> enemyList = boardController.enemiesInRange(this, range);
            while (hits < hitCount && enemyList.size() > 0) {
                hits++;
                int randomValue = this.generator.generate(enemyList.size());
                Enemy e = enemyList.get(randomValue);

                this.visit(e);
                hits++;
            }
            messageCallback.send("Blizzard casted, dealing " + this.spellPower + " damage to all enemies in range");
        } }

    @Override
    public String description() {
        return String.format(this.name + "-      health: " + this.health.getCurrent()+"/"+this.health.getCapacity()+"  attack: "+ this.attack + "  defense: " + this.defense + "  level: " + this.level + "  experience: " + this.experience + "/" + this.levelRequirement() +"   mana : " + this.current_mana+"/"+this.manaPool+"   spell power: "+this.spellPower);
    }

    public void onTick(Tile tile) {
        super.onTick(tile);
        //not sure
        this.current_mana=Math.min(this.current_mana*level,this.manaPool);

    }
    public int getManaPool() {
        return manaPool;
    }
    public int getCurrentMana() {
        return current_mana;
    }

}

