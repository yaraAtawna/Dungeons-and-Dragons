package model.tiles.units;

import model.tiles.Empty;
import model.tiles.Tile;
import model.tiles.Wall;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;
import utils.Health;
import utils.Position;
import utils.callbacks.DeathCallback;
import utils.callbacks.MessageCallback;
import utils.generators.Generator;

import java.util.ArrayList;
import java.util.List;

public abstract class Unit extends Tile {
    protected String name;
    protected Health health;
    protected int attack;
    protected int defense;

    protected Generator generator;
    protected DeathCallback deathCallback;
    protected MessageCallback messageCallback;
    //new
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public Unit(char tile, String name, int hitPoints, int attack, int defense) {
        super(tile);
        this.name = name;
        this.health = new Health(hitPoints);
        this.attack = attack;
        this.defense = defense;
    }

    public Unit initialize(Position p, Generator generator, DeathCallback deathCallback, MessageCallback messageCallback){
        super.initialize(p);
        this.generator = generator;
        this.deathCallback = deathCallback;
        this.messageCallback = messageCallback;
        return this;
    }

    public int attack(){

        return generator.generate(attack);
        //message
    }

    public int defend(){
        return generator.generate(defense);
    }

    public boolean alive(){
        return health.getCurrent() > 0;
    }

    public void battle(Unit enemy) {
        //check this
        messageCallback.send("battle");
        int attack = this.attack();
        int defense = enemy.defend();
        int damageTaken = enemy.health.takeDamage(attack - defense);
    }

    //new
//    public void battle(Unit enemy,int attack)
//    {
//        int defense = enemy.defend();
//        int damageTaken = enemy.health.takeDamage(attack - defense);
//
//    }


    public void interact(Tile t){
        t.accept(this);
    }

    public void visit(Empty e){
        swapPosition(e);
    }

    public void visit(Wall w){
        // Do nothing
    }

    public abstract void visit(Player p);
    public abstract void visit(Enemy e);
    public abstract String description();

    public abstract void onDeath() ;
//        health.newCurrent(0);
//        deathCallback.onDeath();




    public void onTick(Tile tile){
        interact(tile);
    }

    public int takeDamage(int damage){
        return health.takeDamage(damage);
    }


}
