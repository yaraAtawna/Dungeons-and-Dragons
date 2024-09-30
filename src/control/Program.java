package control;
import control.initializers.LevelInitializer;
import control.initializers.TileFactory;
import model.game.Board;
import model.game.Game;
import model.tiles.units.players.Player;

import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        if (args.length == 0) {

            System.exit(-1);
        }
        String path=args[0];
        Game game = new Game(path,s -> System.out.println(s),() -> System.out.println("Player died"));
        game.start();

    }
}
