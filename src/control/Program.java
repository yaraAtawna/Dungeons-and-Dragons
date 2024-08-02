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
        if (args.length == 0) {

            System.exit(-1);
        }
        //int currLevel=0;
        /*get index from user (read linr from console)
        get the player from players list bt the index */
        //first print!
        //Scanner scanner = new Scanner(System.in);
        //int playerIndex = scanner.nextInt();
        Game game = new Game(args[0]);
        game.start();

    }
}
