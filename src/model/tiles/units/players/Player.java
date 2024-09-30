package model.tiles.units.players;

import model.tiles.Tile;
import model.tiles.units.HeroicUnit;
import model.tiles.units.Unit;
import model.tiles.units.enemies.Enemy;
import utils.Health;
import utils.Position;
import utils.boardController;


public abstract class Player extends Unit implements HeroicUnit {
    public static final char PLAYER_TILE = '@';
    protected static final int LEVEL_REQUIREMENT = 50;
    protected static final int HEALTH_GAIN = 10;
    protected static final int ATTACK_GAIN = 4;
    protected static final int DEFENSE_GAIN = 1;
    private int healthGained=0;
    private int attackGained=0;
    private int defenseGained=0;
    protected int level;
    protected int experience;

    protected  String abilityName;

    public abstract void castAbility();

    public Player(String name, int hitPoints, int attack, int defense) {
        super(PLAYER_TILE, name, hitPoints, attack, defense);
        this.level = 1;
        this.experience = 0;
    }

    public void addExperience(int experienceValue) {
        messageCallback.send(String.format("%s gained %d experience.", name, experienceValue));
        this.experience += experienceValue;
        while (experience >= levelRequirement()) {
            levelUp();
        }
    }
    public int getLevel(){
        return level;
    }
    public void levelUp() {
        messageCallback.send(String.format("%s leveled up.", name));
        this.experience -= levelRequirement();
        this.level++;
        int healthGain = healthGain();
        int attackGain = attackGain();
        int defenseGain = defenseGain();
        health.increaseMax(healthGain);
        health.heal();
        attack += attackGain;
        defense += defenseGain;
        healthGained+=healthGain;
        attackGained+=attackGain;
        defenseGained+=defenseGain;

    }

    public int getHealthGained() {
        return healthGained;
    }
    public int getAttackGained() {
        return attackGained;
    }
    public int getDefenseGained() {
        return defenseGained;
    }
    public void setAttackGain(){
        attackGained=0;
    }
    public void setDefenseGain(){
        defenseGained=0;
    }
    public void setHealthGain(){
        healthGained=0;
    }

    protected int levelRequirement() {
        return LEVEL_REQUIREMENT * level;
    }

    protected int healthGain() {
        return HEALTH_GAIN * level;
    }

    protected int attackGain() {
        return ATTACK_GAIN * level;
    }

    protected int defenseGain() {
        return DEFENSE_GAIN * level;
    }

    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }

    public void visit(Player p) {
        // Do nothing
    }

    //visit=battle
    public void visit(Enemy e) {
        System.out.println("test");
        Position pos = e.getPosition();

        battle(e);
        if (!e.alive()) {
            messageCallback.send(String.format("%s you killed enemy.", name));
            addExperience(e.experienceValue());

            boardController.swapPos(this, pos);

        }

    }

    @Override
    public void onDeath() {
        //TODO: Implement onDeath
        //just send out the message
        health.newCurrent(0);
        deathCallback.onDeath();
    }

    public void onTick(Tile tile) {
        super.onTick(tile);

    }
    public int getExperience(){
        return this.experience;
    }
    public Health getHealth(){
        return health;
    }
    public int getAttack(){
        return attack;
    }
    public int getDefense(){
        return defense;
    }

}
