package control.initializers;

import model.tiles.Empty;
import model.tiles.Tile;
import model.tiles.Wall;
import model.tiles.units.enemies.Boss;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.enemies.Monster;
import model.tiles.units.enemies.Trap;
import model.tiles.units.players.Player;
import utils.Position;
import utils.callbacks.DeathCallback;
import utils.callbacks.MessageCallback;
import utils.generators.Generator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import model.tiles.units.players.*;

public class TileFactory {
    private Player p;
    private static final List<Supplier<Player>> playerTypes = Arrays.asList(
            () -> new Warrior("Jon Snow",300,30,4,3),
            () -> new Warrior("The Hound",400,20,6,5),
            () -> new Mage("Melisandre",100,5,1,300,30,15,5,6)   ,
            () -> new Mage("Thoros of Myr",250,25,4,150,20,20,3,4),
            () -> new Rogue("Arya Stark",150,40,2,20),
            () -> new Rogue("Bronn",250,35,3,50),
            () -> new Hunter("Ygritte",220,30,2,6)
    );

    private static final Map<Character, Supplier<Enemy>> enemyTypes = Map.ofEntries(
            Map.entry('s', () -> new Monster('s', "Lannister Solider", 80, 8, 3, 25, 3)),
            Map.entry('k', () -> new Monster('k', "Lannister Knight", 200, 14, 8, 50, 4)),
            Map.entry('q', () -> new Monster('q', "Queen’sGuard", 400, 20, 15, 100, 5)),
            Map.entry('z', () -> new Monster('z', "Wright", 600, 30, 15, 100, 3)),
            Map.entry('b', () -> new Monster('b', "Bear-Wright", 1000, 75, 30, 250, 4)),
            Map.entry('g', () -> new Monster('g', "Giant-Wright", 1500, 100, 40, 500, 5)),
            Map.entry('w', () -> new Monster('w', "White Walker", 2000, 150, 50, 1000, 6)),
            Map.entry('M', () -> new Boss('M', "The Mountain", 1000, 60, 25, 500, 6,5)),
            Map.entry('C', () -> new Boss('C', "Queen Cersei", 100, 10, 10, 1000, 1,8)),
            Map.entry('K', () -> new Boss('K', "Night's King", 5000, 300, 150, 5000, 8,3)),
            Map.entry('B', () -> new Trap('B', "Bouns Trap", 1, 1, 1, 250, 1, 5)),
            Map.entry('Q', () -> new Trap('Q', "Queen's Trap", 250, 50, 10, 100, 3, 7)),
            Map.entry('D', () -> new Trap('D', "Death Trap", 500, 100, 20, 250, 1, 10))
    );
    private static final Map<Character, Supplier<Trap>> TrapTypes = Map.ofEntries(
            Map.entry('B', () -> new Trap('B', "Bouns Trap", 1, 1, 1, 250, 1, 5)),
            Map.entry('Q', () -> new Trap('Q', "Queen's Trap", 250, 50, 10, 100, 3, 7)),
            Map.entry('D', () -> new Trap('D', "Death Trap", 500, 100, 20, 250, 1, 10))
    );
    public TileFactory()
    {
    }

    public Player producePlayer(int playerID){
        Supplier<Player> supp = playerTypes.get(playerID-1);
        this.p = supp.get();
        return this.p;
    }

    public Enemy produceEnemy(char charr, Position p, DeathCallback c, Generator g, MessageCallback m){
        Enemy e = enemyTypes.get(charr).get();
        e.initialize(p, g, c, m);
        return e;
    }


    public Tile produceEmpty(Position p){
        return new Empty().initialize(p);
    }

    public Tile produceWall(Position p){
        return new Wall().initialize(p);
    }

    //new
    public List<Player> getListPlayers() {
        return playerTypes.stream().map(Supplier::get).collect(Collectors.toList());
    }
    //new
    public List<Enemy> getEnemyList() {
        return enemyTypes.values().stream()
                .map(Supplier::get)
                .collect(Collectors.toList());
    }

}
