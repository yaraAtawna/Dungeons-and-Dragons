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

    // update 3/8
    public void start() {

        while (!levelIsOver() && player.alive()) {
            // onGameTick for each enemy and for player
            //player.onGameTick();
            Position p=player.getPosition();
            //player movement
            Tile t=getTile();
            if(t!=null)
            {   player.onTick(t);
                if ((!player.alive()))
                {
                  //player.setPlayerDeathCallBack();
                  TileFactory factory = new TileFactory();
                  Tile dummy=player;
                  Tile tile=dummy.setTile('X');
                 board.update(p,tile);
                 return;
                }
            }
            for (Enemy enemy : enemies) {
                enemy.onTick(player);
            }
            enemysDead();
            printBoard();
            printCombatInfo();
            //(Whole stats for both units, attack roll, defense roll, damage taken, death and
            //experience gained)
        }
    }

    private void printCombatInfo() {
        //need to implement
    }

    //new
    public Tile getTile() {
        try {
            Scanner scanner = new Scanner(System.in);
            char command=scanner.next().charAt(0);
            Position pos = player.getPosition();
            int x = pos.getX();
            int y = pos.getY();
            Position newPos=new Position(x,y);
            Tile t=null;
            switch (command) {
                case 'w': // up
                    y++;
                    break;
                case 's': // down
                    y--;
                    break;
                case 'a': //left
                    x--;
                    break;
                case 'd': // right
                    x++;
                    break;
                case 'e': // cast
                    try {
                        player.castAbility();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
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
                board.update(enemy.getPosition(),factory.produceEmpty(enemy.getPosition()));
                board.remove(enemy);
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
