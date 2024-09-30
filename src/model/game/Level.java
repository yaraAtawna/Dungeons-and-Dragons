package model.game;

import control.initializers.TileFactory;
import model.tiles.Empty;
import model.tiles.Tile;
import model.tiles.units.Unit;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.enemies.Trap;
import model.tiles.units.players.Player;
import utils.Position;
import utils.callbacks.DeathCallback;
import utils.callbacks.MessageCallback;
import utils.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Level {
    private Player player;
    private List<Enemy> enemies;

    private Board board;
    private MessageCallback messageCallback;
    private DeathCallback deathCallback;

    // update 15/7
    public Level(Board board, MessageCallback messageCallback, DeathCallback deathCallback) {
        this.player = board.getPlayer();
        this.enemies = board.getEnemies();

        this.board = board;
        this.messageCallback = messageCallback;
        this.deathCallback = deathCallback;
        boardController.gameBoard = board;
    }

    public void start() {

        while (!levelIsOver() && player.alive()) {

            Position p = player.getPosition();

            Tile t = getTile();

            if (t != null) {
                player.onTick(t);
                messageCallback.send(player.description());
                if ((!player.alive())) {
                    //player.setPlayerDeathCallBack();
                    TileFactory factory = new TileFactory();
                    Tile dummy = player;
                    Tile tile = dummy.setTile('X');
                    tile.initialize(p);
                    board.update(p, tile);
                    return;
                }


            }

            // temporarily commented
            for (Enemy enemy : enemies) {
                enemy.onTick(player);
            }
            enemysDead();
            printBoard();

        }
        messageCallback.send(player.name +" reached level " + player.getLevel() +":" +"+"+player.getHealthGained()+" Health, +"+player.getAttackGained()+" Attack, +"+player.getDefenseGained() +"Defense");
        player.setAttackGain();
        player.setDefenseGain();
        player.setHealthGain();
    }

    public Tile Move(char c){
        try {
            Position pos = player.getPosition();
            char command = c;
            int x = pos.getX();
            int y = pos.getY();
            Tile t;

            switch (command) {
                case 'w': // up
                    x--;
                    break;
                case 's': // down
                    x++;
                    break;
                case 'a': //left
                    y--;
                    break;
                case 'd': // right
                    y++;
                    break;
                case 'e': // cast
                    try {
                        player.castAbility();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            t = board.getTile(new Position(x, y));
            if (t == null && command != 'e' && x < board.getWidth() && y < board.getHeight() && x >= 0 && y >= 0) {
                Position p = new Position(x, y);

                board.swapPos(board.getTile(pos), p);
                t = board.getTile(p);
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //new
    public Tile getTile() {
        try {
            Scanner scanner = new Scanner(System.in);
            char command = scanner.next().charAt(0);
            Position pos = player.getPosition();
            int x = pos.getX();
            int y = pos.getY();
            Tile t;
            switch (command) {
                case 'w': // up
                    x--;
                    break;
                case 's': // down
                    x++;
                    break;
                case 'a': //left
                    y--;
                    break;
                case 'd': // right
                    y++;
                    break;
                case 'e': // cast
                    try {
                        player.castAbility();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            t = board.getTile(new Position(x, y));
            if (t == null && command != 'e' && x < board.getWidth() && y < board.getHeight() && x >= 0 && y >= 0) {
                Position p = new Position(x, y);
                board.swapPos(board.getTile(pos), p);
                t = board.getTile(p);
            }

            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void enemysDead() {
        LinkedList<Enemy> enemies = new LinkedList<>(board.getEnemies());
        for (Enemy enemy : enemies) {
            if (enemy.isDead()) {
                TileFactory factory = new TileFactory();
                Tile em = new Empty();
                em.getPosition().setPos(enemy.getPosition().getX(), enemy.getPosition().getY());
                board.update(enemy.getPosition(), em);
                board.remove(enemy);
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
