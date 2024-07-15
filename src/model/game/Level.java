package model.game;

import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;

import java.util.List;

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

    // update 15/7
    public void start() {
        while (!levelIsOver() && !gameIsOver()) {
            // onGameTick for each enemy and for player
            for (Enemy enemy : enemies) {
                enemy.onGameTick();
            }
            player.onGameTick();
        }
    }

    private boolean levelIsOver() {
         Define logic to determine if the level is over
        return false;
    }

    private boolean gameIsOver() {
         Define logic to determine if the game is over
        return false;
    }

}
