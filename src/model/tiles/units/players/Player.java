package model.tiles.units.players;

import model.tiles.Tile;
import model.tiles.units.Unit;
import model.tiles.units.enemies.Enemy;
import utils.Position;
import utils.boardController;

import java.util.List;

public abstract class Player extends Unit
{
    public static final char PLAYER_TILE = '@';
    protected static final int LEVEL_REQUIREMENT = 50;
    protected static final int HEALTH_GAIN = 10;
    protected static final int ATTACK_GAIN = 4;
    protected static final int DEFENSE_GAIN = 1;

    protected int level;
    protected int experience;

    protected String abilityName;
    public abstract void castAbility();

    public Player(String name, int hitPoints, int attack, int defense) {
        super(PLAYER_TILE, name, hitPoints, attack, defense);
        this.level = 1;
        this.experience = 0;
    }

    public void addExperience(int experienceValue){
        this.experience += experienceValue;
        while (experience >= levelRequirement()) {
            levelUp();
        }
    }

    public void levelUp(){
        this.experience -= levelRequirement();
        this.level++;
        int healthGain = healthGain();
        int attackGain = attackGain();
        int defenseGain = defenseGain();
        health.increaseMax(healthGain);
        health.heal();
        attack += attackGain;
        defense += defenseGain;
    }

    protected int levelRequirement(){
        return LEVEL_REQUIREMENT * level;
    }

    protected int healthGain(){
        return HEALTH_GAIN * level;
    }

    protected int attackGain(){
        return ATTACK_GAIN * level;
    }

    protected int defenseGain(){
        return DEFENSE_GAIN * level;
    }

    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }

    public void visit(Player p){
        // Do nothing
    }

    //visit=battle
    public void visit(Enemy e){
        Position pos = e.getPosition();
        battle(e);
        if(!e.alive()){
            messageCallback.send(String.format("%s you killed enemy.", name));
            addExperience(e.experienceValue());
            boardController.swapPosEnemy(this, pos);
            this.position.setPos(pos.getX(), pos.getY());
            //messageCallback.send(String.format("%s enemy died.", name));
        }
    }

    @Override
    public void onDeath()
    {
        //TODO: Implement onDeath
        //just send out the message
        health.newCurrent(0);
        deathCallback.onDeath();
    }

    public void onTick(Tile tile)
    {
        super.onTick(tile);

    }


}
