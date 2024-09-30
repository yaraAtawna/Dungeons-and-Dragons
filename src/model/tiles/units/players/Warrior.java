package model.tiles.units.players;

import  model.game.Board;
import model.tiles.Tile;
import model.tiles.units.enemies.Enemy;
import utils.generators.RandomGenerator;
import utils.*;

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
        this.abilityName ="Avenger’s Shield";
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

    @Override
    public String description() {
        return String.format(this.name + "-      health: " + this.health.getCurrent()+"/"+this.health.getCapacity()+"  attack: "+ this.attack + "  defense: " + this.defense + "  level: " + this.level + "  experience: " + this.experience + "/" + this.levelRequirement() +"   cooldown : " + this.remainingCooldown+"/"+this.cooldown);

    }


    public void onTick(Tile tile)
    {
        super.onTick(tile);
        this.remainingCooldown=Math.max(0,this.remainingCooldown-1);
    }


    public void castAbility() {


        if(this.remainingCooldown>0){
            messageCallback.send("You cant cast  special ability , cooldown remaining: "+this.remainingCooldown);
        }
        else {
            messageCallback.send("cast  special ability : " + this.abilityName);
            this.remainingCooldown = this.cooldown;
            int cap = this.health.getCurrent() + ABILITY_DEFENSE * this.defense;
            int current = Math.min(cap, this.health.getCapacity());
            this.health.newCurrent(current);


            List<Enemy> enemiesRange = boardController.enemiesInRange(this, ABILITY_RANGE);

            if (enemiesRange.size() > 0) {
                int randomValue = this.generator.generate(enemiesRange.size());
                Enemy e = enemiesRange.get(randomValue);

                int wAttack = (int) (ABILITY_HIT * this.health.getCapacity());

                messageCallback.send(this.name +" rolled " + wAttack+ " attack points.");
                int defense =e.defend();

                int damage = wAttack - defense;
                if (damage > 0) {
                    messageCallback.send(String.format("%s dealt %d damage to %s", this.name, damage, e.name));
                    e.takeDamage(damage);
                }
                Position pos = e.getPosition();
                if (!e.alive()) {
                    messageCallback.send(String.format("%s you killed enemy.", name));
                    addExperience(e.experienceValue());
//                    System.out.println("pos: " + pos);
                    boardController.swapPos(this, pos);

//                    System.out.println("position: " + position);

                }


            }
        }
    }

}
