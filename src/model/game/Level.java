package model.game;

import control.initializers.TileFactory;
import model.tiles.Tile;
import model.tiles.units.Unit;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;
import utils.Position;
import utils.callbacks.DeathCallback;
import utils.callbacks.MessageCallback;
import utils.*;
import java.util.List;
import java.util.Scanner;

public class Level
{
    private Player player;
    private List<Enemy> enemies;
    private Board board;
 private MessageCallback messageCallback;
    private DeathCallback deathCallback;

    // update 15/7
    public Level(Board board, MessageCallback messageCallback, DeathCallback deathCallback)
    {
        this.player = board.getPlayer();
        this.enemies = board.getEnemies();
        this.board = board;
        this.messageCallback = messageCallback;
        this.deathCallback = deathCallback;
        boardController.gameBoard = board;
    }

    public void start() {

        while (!levelIsOver() && player.alive()) {
            // onGameTick for each enemy and for player
            Position p=player.getPosition();
//            System.out.println("player position : "+p.getX() +", "+p.getY())  ;
            //player movement
            Tile t=getTile();
//            if(t==null )
//            {
//                System.out.println("tile = null");
//
//            }
            if(t!=null)
            {
//                System.out.println("player old position : "+player.getPosition().getX() +", "+player.getPosition().getY())  ;
//                System.out.println("tile old position : "+t.getPosition().getX() +", "+t.getPosition().getY())  ;
                player.onTick(t);
//                System.out.println("player new position : "+player.getPosition().getX() +", "+player.getPosition().getY())  ;
//                System.out.println("tile new position : "+t.getPosition().getX() +", "+t.getPosition().getY())  ;
//
//                System.out.println("tile : ");
//                System.out.println(board.getTile(t.getPosition()));
                if ((!player.alive()))
                {
                  //player.setPlayerDeathCallBack();
                  TileFactory factory = new TileFactory();
                  Tile dummy=player;
                  Tile tile=dummy.setTile('X');
                 board.update(p,tile);
                 return;
                }
                //to do : create emptyTile and put in old pos in board!
//                System.out.println("tile : ");
                //System.out.println(board.getTile(t.getPosition()));
//                Position pos = t.getPosition();
//                if(board.getTile(pos)!=null)
//                {
//                    System.out.println("tile is not null");
//                }
//                else
//                {
//                    System.out.println("tile is null");
                    //create empty tile
//                }
//                if(board.getTile(pos)==null)
//                {
//                    System.out.println("tile is still null");
//                }

            }
           // temporarily commented
            for (Enemy enemy : enemies)
            {
                System.out.println("new enemy ")  ;
                enemy.onTick(player);
            }
            enemysDead();
            printBoard();

        }
    }



    //new
    public Tile getTile() {
        try {
            Scanner scanner = new Scanner(System.in);
            char command=scanner.next().charAt(0);
            Position pos = player.getPosition();
            int x = pos.getX();
            int y = pos.getY();
            //Position newPos=new Position(x,y);
            Tile t=null;
//            System.out.println("getTile in level : ");
//            System.out.println("current : " + x+" ,"+y);
            switch (command) {
                case 'w': // up
                    y--;
//                    System.out.println("up " + x + "  " + y);
                    break;
                case 's': // down
                    y++;
//                    System.out.println("down " + x + " " + y);
                    break;
                case 'a': //left
                    x--;
//                    System.out.println("left " + x + "  " + y);
                    break;
                case 'd': // right
                    x++;
//                    System.out.println("right " + x + "  " + y);
                    break;
                case 'e': // cast
                    try {
                        player.castAbility();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            t=board.getTile(new Position(x,y));
            if(t==null && command!='e' && x<board.getWidth() && y<board.getHeight() && x>=0 && y>=0)
            {
                Position p=new Position(x,y);
                //System.out.println("tile is null");
                board.addEmptyTile(p);
                t=board.getTile(p);
            }
//            {
//                System.out.println("tile is null");
//            }
            return t;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    private void enemysDead() {
        for (Enemy enemy: board.getEnemies())
        {
            if (enemy.isDead()){
                TileFactory factory = new TileFactory();
                board.remove(enemy);
                board.update(enemy.getPosition(),factory.produceEmpty(enemy.getPosition()));
                //enemy.setEnemyDeathCallBack();
            }
        }
    }
    private void printBoard() {
        // Print the board
        messageCallback.send(board.toString());
    }

    private boolean levelIsOver() {
        return board.getEnemies().isEmpty();
    }


    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public Board getBoard() {
        return board;
    }
     public void setPlayer(Player player) {
        this.player = player;
}


}
