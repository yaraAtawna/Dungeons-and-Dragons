package model.game;

import control.initializers.TileFactory;
import model.tiles.Tile;
import model.tiles.units.Unit;
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
    private TileFactory tileFactory;

    public Board(List<Tile> tiles, Player p, List<Enemy> enemies, int width, int height,TileFactory tileFactory) {
        this.player = p;
        this.enemies = enemies;
        this.width = width;
        this.board = new TreeMap<>();
        for(Tile t : tiles){
            board.put(t.getPosition(), t);
        }
        this.height = height;
        this.tileFactory=tileFactory;
    }

    @Override
    public String toString() {
        //System.out.println("testing Board toString");
        StringBuilder sb = new StringBuilder();
        //initialize boardArray
        String [][] boardArray = new String[height][width];
        for (int y = 0; y < height; y=y+1) {
            for (int x = 0; x < width; x = x + 1) {
                boardArray[y][x] = ".";
            }
        }
        //System.out.println("testing Board 2");

        //fill boardArray with tiles
        for (Map.Entry<Position, Tile> entry : board.entrySet()) {
            Tile t=entry.getValue();
            boardArray[t.getPosition().getY()][t.getPosition().getX()]=t.toString();
        }

        //print boardArray
        for (String[] line : boardArray) {
            for (String block : line) {
                sb.append(block);
            }
            //System.out.println("new line");
            sb.append("\n");
        }
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
    public  void swapPosWithEnemy(Unit t, Position position)
    {
        enemies.remove(board.get(position));
        board.remove(t.getPosition());
        board.remove(position);
        board.put(position,t);

    }
    public  void swapPos(Tile t,Position position)
    {

        Position oldPos=t.getPosition();
        Tile tile=board.get(position);
        board.remove(oldPos);
        board.remove(position);
        board.put(position,t);
        tile.getPosition().setPos(oldPos.getX(), oldPos.getY());
        board.put(oldPos, tile);

    }
    public void remove(Enemy enemy)
    {
        enemies.remove(enemy);
    }

    public void addEmptyTile(Position position) {
        Tile t=tileFactory.produceEmpty(position);
        board.put(position,t);
    }
}
