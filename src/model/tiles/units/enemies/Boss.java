package model.tiles.units.enemies;

import model.tiles.Tile;
import model.tiles.units.HeroicUnit;
import model.tiles.units.players.Player;
import utils.boardController;
import view.CLI;

public class Boss extends Monster implements HeroicUnit {
    // implements the HeroicUnit interface.  behave mostly like monsters
    private int abilityFrequency;
    private int combatTick;

    public Boss(char tile, String name, int hitPoints, int attack, int defense, int experienceValue,int visionRange,int ability) {
        super(tile, name, hitPoints, attack, defense, experienceValue,visionRange);
        this.abilityFrequency=ability;
        this.combatTick=0;
    }
    public void onTick( Player p)
    {
        Tile tile=null;
        double range=this.position.range(p.getPosition());
        if(range<vision)
        {

            if(this.combatTick==abilityFrequency)
            {
                combatTick=0;
                castAbility();
            }
            else
            {
                combatTick++;
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

        }
        else
        {
            combatTick=0;
            //random move
            int randomValue = this.generator.generate(4);
            switch (randomValue)
            {
                case 0 ->  tile= boardController.move(Direction.LEFT,this);
                case 1 ->  tile=boardController.move(Direction.RIGHT,this);
                case 2 -> tile=boardController.move(Direction.UP,this);
                case 3 -> tile=boardController.move(Direction.DOWN,this);
                case 4 -> {  //do nothing
                }

            }
        }


    }
    @Override
    public void castAbility() {

        Player p=boardController.gameBoard.getPlayer();
        double range=this.position.range(p.getPosition());
        if(range<vision) {

            this.Bossbattle(p, attack);
        }
    }
    public String description() {
        return String.format(this.name + "-      health: " + this.health.getCurrent()+"/"+this.health.getCapacity()+"  attack: "+ this.attack + "  defense: " + this.defense +"    Experience Value: "+this.experienceValue +"   vision: "+this.vision);
    }
}
