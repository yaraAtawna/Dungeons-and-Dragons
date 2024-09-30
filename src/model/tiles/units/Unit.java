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
import utils.generators.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

public abstract class Unit extends Tile {
    public String name;
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
        this.generator=new RandomGenerator();
        this.messageCallback=s -> System.out.println(s);
        this.deathCallback=()-> System.out.println("player died");
    }

    public Unit initialize(Position p, Generator generator, DeathCallback deathCallback, MessageCallback messageCallback){
        super.initialize(p);
        this.generator = generator;
        this.deathCallback = deathCallback;
        this.messageCallback = messageCallback;
        return this;
    }

    public int attack(){
        int TempAttack = generator.generate(this.attack+1);
        messageCallback.send(this.name +" rolled " + TempAttack+ " attack points.");
        return TempAttack;
        //message
    }

    public int defend(){
        int TempDefense = generator.generate(this.defense+1);
        messageCallback.send(this.name +" rolled "+TempDefense+ " defense points.");
        return TempDefense;
    }

    public boolean alive(){
        return health.getCurrent() > 0;
    }

    public void battle(Unit enemy) {

        messageCallback.send(this.name+"  engaged in combat with "+enemy.name);
        messageCallback.send(this.description());
        messageCallback.send(enemy.description());
        int attack = this.attack();
        int defense = enemy.defend();
        int damageTaken = attack - defense;
        if (damageTaken > 0){
            int damageDealt = enemy.takeDamage(damageTaken);
            messageCallback.send(String.format("%s dealt %d damage to %s", this.name, damageTaken, enemy.name));
        } else {
            messageCallback.send(String.format("%s missed %s", this.name, enemy.name));
        }

    }


    public void Bossbattle(Unit defender,int attack)
    {
        int defense = defender.defend();
        if(attack-defense>0) {
            int damageTaken = defender.health.takeDamage(attack - defense);
        }

    }


    public void interact(Tile t){

        t.accept(this);
    }

    public void visit(Empty e){
        int x = e.getPosition().getX();
        int y = e.getPosition().getY();
        swapPosition(this, e.getPosition());

        this.position.setPos(x, y);

    }

    public void visit(Wall w){
        // Do nothing
    }

    public abstract void visit(Player p);
    public abstract void visit(Enemy e);
    public abstract String description();

    public abstract void onDeath() ;





    public void onTick(Tile tile){
        interact(tile);
    }

    public int takeDamage(int damage){

        return health.takeDamage(damage);
    }
    public Health getHealth(){
        return this.health;
    }

}
