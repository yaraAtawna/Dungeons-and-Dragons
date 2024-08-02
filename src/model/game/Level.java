package model.game;

import control.initializers.TileFactory;
import model.tiles.Tile;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;
import utils.Position;

import java.util.List;
import java.util.Scanner;

public class Level
{
    private Player player;
    private List<Enemy> enemies;
    private Board board;


    // update 15/7
    public Level( Board board)
    {
        this.player = board.getPlayer();
        this.enemies = board.getEnemies();
        this.board = board;
    }

    // update 3/8
    public void start() {

        while (!levelIsOver() && !gameIsOver()) {
            // onGameTick for each enemy and for player
            //player.onGameTick();
            playerTick(player, board, enemies);

            for (Enemy enemy: board.getEnemies())
            {
                if (enemy.isDead()){
                    TileFactory factory = new TileFactory();
                    board.update(enemy.getPosition(),factory.produceEmpty(enemy.getPosition()));
                    board.getEnemies().remove(enemy);
                    //enemy.setEnemyDeathCallBack();
                }
            }
            if ((player.isDead())) {
                //player.setPlayerDeathCallBack();
                return;
            }
            for (Enemy enemy : enemies) {
                enemy.onTick(player);
            }
            printBoard();
        }
    }
        //new
    private void playerTick(Player p, Board board, List<Enemy> enemyList) {
        try {
            Scanner scanner = new Scanner(System.in);
            char command=scanner.next().charAt(0);
            Position pos = p.getPosition();
            Position newPos;
            int x = pos.getX();
            int y = pos.getY();
            switch (command) {
                case 'w': // up
                    y++;
                    newPos = new Position(x, y);
                    move(board, p, newPos);
                    break;
                case 's': // down
                    y--;
                    newPos = new Position(x, y);
                    move(board, p, newPos);
                    break;
                case 'a': //left
                    //Move(board, p, LEFT);
                    x--;
                    newPos = new Position(x, y);
                    move(board, p, newPos);
                    break;
                case 'd': // right
                    x++;
                    newPos = new Position(x, y);
                    move(board, p, newPos);
                    break;
                case 'e': // cast
                    try {
                        player.castAbility(enemyList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void move(Board board, Player p, Position pos)
    {
        Tile tile = board.getTile(pos);
        player.onTick(tile);
    }
    private void printBoard() {
    }

    private boolean levelIsOver() {

        return false;
    }

    private boolean gameIsOver() {
        return false;
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
}   }
