package utils;

import model.game.Board;
import model.tiles.Tile;
import model.tiles.units.Unit;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;
import utils.callbacks.MessageCallback;

import java.util.ArrayList;
import java.util.List;

public class boardController {
    public static Board gameBoard;
    public static MessageCallback messageCallBack;

    public static Tile move(Unit.Direction direction, Unit unit){
        int x = unit.getPosition().getX();
        int y = unit.getPosition().getY();
        switch (direction) {
            case UP -> x--;
            case DOWN -> x++;
            case LEFT -> y--;
            case RIGHT -> y++;
        }
        Position newPosition=new Position(x,y);
        if(!validPosition(newPosition))
            return null;
        Tile tile=gameBoard.getTile(newPosition);
        unit.getPosition().setPos(x,y);


        return tile;
    }


    public static List<Enemy> enemiesInRange(Player p, int range){
        List<Enemy> ans=new ArrayList<>();
        List<Enemy> enemies=gameBoard.getEnemies();
        for(Enemy enemy:enemies){
            if(p.getPosition().range(enemy.getPosition())<range)
                ans.add(enemy);
        }
        return ans;
    }


    public static boolean validPosition(Position position){
        if(position.getX()<0 | position.getX()> gameBoard.getWidth() | position.getY()<0 | position.getY()> gameBoard.getHeight())
            return false;

        return true;
    }
}