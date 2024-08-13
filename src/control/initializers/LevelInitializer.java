package control.initializers;

import model.game.Board;
import model.tiles.Tile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;
import utils.Position;
import utils.callbacks.DeathCallback;
import utils.callbacks.MessageCallback;
import utils.generators.FixedGenerator;
import utils.generators.Generator;

public class LevelInitializer {
    private static int playerID;

    //new
    private static int levelsNum;
    //private List<String> lines;
    private static TileFactory factory ;


    DeathCallback deathCallback;
    Generator generator;
    MessageCallback messageCallback;

    public LevelInitializer(int playerID, MessageCallback messageCallback, DeathCallback deathCallback) {
        this.playerID = playerID;
        this.levelsNum=0;
        factory = new TileFactory();
        this.deathCallback = deathCallback;
        this.generator = new FixedGenerator();
        this.messageCallback = messageCallback;


    }
    //new-return type Board
    // updated
    public Board initLevel(String levelPath){
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(levelPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //new
        List<Tile> tiles = new ArrayList<>();
        List<Enemy> enemies = new ArrayList<>();
        Player player = null;
        levelsNum=lines.size();

        //int x=0;
        int y=0;
        for(String line : lines){
            int x=0;
            for(char c : line.toCharArray()){
                Position pos = new Position(x, y);
                x++;
                switch(c) {
                    case '.':
                        // create empty tile
                        tiles.add(factory.produceEmpty(pos));
                        break;
                    case '#':
                        // create wall tile
                        tiles.add(factory.produceWall(pos));
                        break;
                    case '@':
                        // create player tile
                        player = factory.producePlayer(playerID);
                        player.initialize(pos); // check!
                        tiles.add(player);
                        break;
                    default:
                        // create enemy tile
                        // DeathCallback c, Generator g, MessageCallback m
                        Enemy enemy = factory.produceEnemy(c, pos, this.deathCallback, this.generator, this.messageCallback); // provide appropriate callbacks
                        enemies.add(enemy);
                        tiles.add(enemy);
                        break;
                }

            }
            y++;
        }
        //List<Tile> tiles, Player p, List<Enemy> enemies, int width
        int width = lines.isEmpty() ? 0 : lines.get(0).length(); // Determine the width of the level
        int highest = lines.size(); // Determine the height of the level
        //test
//        String test="width: "+width+" highest: "+highest;
//        messageCallback.send("test in LevelInitializer: ");
//        messageCallback.send(test)  ;
        return new Board(tiles, player, enemies, width, highest);

    }

    // updated
    public int getLevelsNum()
    {
        return levelsNum;
    }

    /*
    old version:
     for(String line : lines){
            for(char c : line.toCharArray()){
                switch(c) {
                    case '.':
                        // create empty tile
                        tiles.add(factory.produceEmpty(pos));

                        break;
                    case '#':
                        // create wall tile
                        break;
                    case '@':
                        // create player tile
                        break;
                    default:
                        // create enemy tile
                        break;
                }
            }
        }
     */
}
