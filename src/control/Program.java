package control;
import control.initializers.LevelInitializer;
import control.initializers.TileFactory;
import model.game.Board;
import model.game.Game;
import model.tiles.units.players.Player;

import java.util.List;
import java.util.Scanner;

// update 15/7
public class Program {
    public static void main(String[] args) {
//        if (args.length == 0) {
//
//            System.exit(-1);
//        }
        String path="C:\\Users\\yaraa\\OneDrive\\שולחן העבודה\\hw3\\out\\artifacts\\levels_dir\\"; //only for testing
        //String path=args[0]; fo jar
        Game game = new Game(path,s -> System.out.println(s),() -> System.out.println("Player died"));
        game.start();

    }
}
