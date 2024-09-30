package model.game;

import control.initializers.TileFactory;
import model.tiles.Empty;
import model.tiles.Tile;
import model.tiles.units.Unit;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.enemies.Trap;
import model.tiles.units.players.Player;
import utils.Position;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Board {
    private Tile[][] board;
    private Player player;
    private List<Enemy> enemies;
    private final int width;
    private final int height;
    private TileFactory tileFactory;


    public Board(List<Tile> tiles, int rows, int column, Player p, List<Enemy> enemies, int width, int height, TileFactory tileFactory) {
        this.player = p;
        this.enemies = enemies;
        this.width = width;
        this.board = new Tile[rows][column];

        int i = 0;
        for (Tile t : tiles) {
            board[t.getPosition().getX()]
                    [t.getPosition().getY()] = t;
        }
        this.height = height;
        this.tileFactory = tileFactory;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        //initialize boardArray
        String[][] boardArray = new String[height][width];
        for (int y = 0; y < height; y = y + 1) {
            for (int x = 0; x < width; x = x + 1) {
                boardArray[y][x] = ".";
            }
        }

        //fill boardArray with tiles
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                Tile t = board[i][j];
                boardArray[t.getPosition().getX()][t.getPosition().getY()] = t.toString();
            }
        }

        //print boardArray
        for (
                String[] line : boardArray) {
            for (String block : line) {
                sb.append(block);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    //new
    public List<Enemy> getEnemies() {
        return enemies;
    }

    public Player getPlayer() {
        return player;
    }

    public Tile getTile(Position p) {
        return board[p.getX()][p.getY()];
    }

    public void update(Position p, Tile t) {
        board[p.getX()][p.getY()] = t;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        //fix this!
        return height;
    }

    public void swapPosWithEnemy(Unit t, Position position) {
        enemies.remove(board[position.getX()][position.getY()]);
        Tile empty = new Empty();
        empty.getPosition().setPos(t.getPosition().getX(), t.getPosition().getY());
        board[position.getX()][position.getY()] = empty;
        board[position.getX()][position.getY()] = t;
    }

    public void swapPos(Tile t, Position position) {
        Position oldPos = t.getPosition();
        Position temp = new Position(oldPos.getX(), oldPos.getY());
        int tmpX = oldPos.getX();
        int tmpY = oldPos.getY();
        Tile tile = board[position.getX()][position.getY()]; //removed tile

        board[position.getX()][position.getY()] = t;
        board[oldPos.getX()][oldPos.getY()] = tile;

        t.updatePosition(position.getX(), position.getY());
        tile.updatePosition(tmpX, tmpY);
    }

    public void remove(Enemy enemy) {
        enemies.remove(enemy);
    }

    public void addEmptyTile(Position position) {
        Tile t = tileFactory.produceEmpty(position);
        board[position.getX()][position.getY()] = t;
    }

    public void UpdateTrap(Tile t, Position position) {
        board[position.getX()][position.getY()] = t;
        t.getPosition().setPos(position.getX(), position.getY());
    }
}
