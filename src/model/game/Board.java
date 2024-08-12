package model.game;

import model.tiles.Tile;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;
import utils.Position;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Board {
    private Map<Position, Tile> board;
    private Player player;
    private List<Enemy> enemies;
    private final int width;
    private final int height;

    public Board(List<Tile> tiles, Player p, List<Enemy> enemies, int width, int height){
        this.player = p;
        this.enemies = enemies;
        this.width = width;
        this.board = new TreeMap<>();
        for(Tile t : tiles){
            board.put(t.getPosition(), t);
        }
        this.height = height;
    }

    @Override
    public String toString() {
        System.out.println("testing Board toString");
        StringBuilder sb = new StringBuilder();
        String [][] boardArray = new String[width][height];
        for(Map.Entry<Position, Tile> entry : board.entrySet()){
            boardArray[entry.getValue().getPosition().getX()-1][entry.getValue().getPosition().getY()-1]=(entry.getValue().toString());
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++)
            {
                sb.append(boardArray[x][y]);
            }

            }

//        for(Map.Entry<Position, Tile> entry : board.entrySet()){
//            sb.append(entry.getValue().toString());
//            if(entry.getKey().getX() == width-1){
//                System.out.println("new line");
//                sb.append("\n");
//            }
//        }

//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                Position pos = new Position(x, y);
//                Tile tile = board.get(pos);
//                if (tile != null) {
//                    sb.append(tile.toString());
//                } else {
//                    //sb.append(" "); // Use space for empty positions
//                }
//            }
//            //                System.out.println("new line");
//            sb.append("\n"); // Newline after each row
//        }

        return sb.toString();
    }
    //new
    public  List<Enemy> getEnemies()
    {
        return enemies;
    }
    public Player getPlayer()
    {
        return player;
    }
    public Tile getTile(Position p)
    {
        return board.get(p);
    }
    public void update(Position p, Tile t)
    {
        board.put(p,t);
    }
    public int getWidth()
    {
        return width;
    }
    public int getHeight()
    {
        //fix this!
        return height;
    }
    public  void swapPos(Tile t,Position position)
    {
        enemies.remove(board.get(position));
        board.remove(t.getPosition());
        board.remove(position);
        board.put(position,t);

    }
    public void remove(Enemy enemy)
    {
        enemies.remove(enemy);
    }

}
