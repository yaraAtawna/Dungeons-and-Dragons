package model.tiles.units.enemies;

import model.tiles.Tile;
import model.tiles.units.players.Player;
import utils.generators.RandomGenerator;
import utils.*;
public class Monster extends Enemy{
    protected int vision;
    public Monster(char tile, String name, int hitPoints, int attack, int defense, int experienceValue,int vision) {
        super(tile, name, hitPoints, attack, defense, experienceValue);
        this.vision=vision;
    }


    public void onTick( Player p)
    {

        double range=this.position.range(p.getPosition());
        Tile tile=null;
        if(range<vision)
        {

            int dx=this.position.getY()-p.getPosition().getY();
            int dy=this.position.getX()-p.getPosition().getX();
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

        if(tile!=null)
        { this.interact(tile);}
    }


    @Override
    public String description() {
        return String.format(this.name + "-      health: " + this.health.getCurrent()+"/"+this.health.getCapacity()+"  attack: "+ this.attack + "  defense: " + this.defense +"    Experience Value: "+this.experienceValue +"   vision: "+this.vision);
    }
}
