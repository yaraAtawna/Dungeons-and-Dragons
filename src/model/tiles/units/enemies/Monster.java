package model.tiles.units.enemies;

import model.tiles.units.players.Player;
import utils.generators.RandomGenerator;

public class Monster extends Enemy{
    private int vision;
    public Monster(char tile, String name, int hitPoints, int attack, int defense, int experienceValue,int vision) {
        super(tile, name, hitPoints, attack, defense, experienceValue);
        this.vision=vision;
    }

    //correct argument???????
    public void onTick(Player p)
    {
        double range=this.position.range(p.getPosition());
        if(range<vision)
        {
            int dx=this.position.getX()-p.getPosition().getX();
            int dy=this.position.getY()-p.getPosition().getY();
            if (Math.abs(dx) > Math.abs(dy))
            {
                if (dx > 0)
                { //move left
                    this.move(Direction.LEFT);
                     }
                else {   //move right
                    this.move(Direction.RIGHT);
                }
            }
            else
            {
                if (dy > 0)
                { //move up
                    this.move(Direction.UP);
                }
                else {   //move down
                    this.move(Direction.DOWN);
                }
            }
        }
        else
        {
            //random move
            //RandomGenerator randomGenerator = new RandomGenerator();
            int randomValue = this.generator.generate(3);
            switch (randomValue)
            {
                case 0 -> this.move(Direction.LEFT);
                case 1 ->  this.move(Direction.RIGHT);
                case 2 -> this.move(Direction.UP);
                case 3 -> this.move(Direction.DOWN);
            }
        }
    }
}
