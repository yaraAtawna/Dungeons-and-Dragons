package model.tiles.units.enemies;

import model.tiles.Tile;
import model.tiles.units.players.Player;
import utils.generators.RandomGenerator;
import utils.*;
public class Monster extends Enemy{
    private int vision;
    public Monster(char tile, String name, int hitPoints, int attack, int defense, int experienceValue,int vision) {
        super(tile, name, hitPoints, attack, defense, experienceValue);
        this.vision=vision;
    }

    //correct argument???????
    public void onTick( Player p)
    {
        double range=this.position.range(p.getPosition());
        Tile tile=null;
        if(range<vision)
        {
            int dx=this.position.getX()-p.getPosition().getX();
            int dy=this.position.getY()-p.getPosition().getY();
            if (Math.abs(dx) > Math.abs(dy))
            {
                if (dx > 0)
                { //move left
                    tile=boardController.move(Direction.LEFT,this);
                     }
                else {   //move right
                    tile=boardController.move(Direction.RIGHT,this);
                }
            }
            else
            {
                if (dy > 0)
                { //move up
                    tile=boardController.move(Direction.UP,this);
                }
                else {   //move down
                    tile=boardController.move(Direction.DOWN,this);
                }
            }
        }
        else
        {
            //random move
            //RandomGenerator randomGenerator = new RandomGenerator();
            int randomValue = this.generator.generate(4);
            switch (randomValue)
            {
                case 0 ->  tile=boardController.move(Direction.LEFT,this);
                case 1 ->  tile=boardController.move(Direction.RIGHT,this);
                case 2 -> tile=boardController.move(Direction.UP,this);
                case 3 -> tile=boardController.move(Direction.DOWN,this);
                case 4 -> {  //do nothing
                }

            }
        }
       // tile.acceptThis
        if(tile!=null)
        { this.interact(tile);}
    }

    @Override
    public String description() {
        return null;
    }
}
