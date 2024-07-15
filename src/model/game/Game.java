package model.game;

import control.initializers.LevelInitializer;
import utils.callbacks.DeathCallback;
import utils.callbacks.MessageCallback;

import java.util.List;

public class Game
{
    //private List<String> levelPaths;
    private int currentLevelIndex;
    private boolean gameIsOver;
    Board board;

    //new
    private  int levelsNum;


    // update 15/7
    public Game(String path, int playerIndex, MessageCallback messageCallback, DeathCallback deathCallback) {
        //this.levelPaths = levelPaths;
        this.currentLevelIndex = 0;
        this.gameIsOver = false;
        LevelInitializer levelInitializer = new LevelInitializer(playerIndex, messageCallback,  deathCallback);
         board = levelInitializer.initLevel(path);
         this.levelsNum=levelInitializer.getLevelsNum();

    }

    // update 15/7
    public void start() {
        while (currentLevelIndex < levelsNum && !gameIsOver) {
            // Load the next level
           not done
            Level level = new Level(board);

            // Start the level
            level.start();

            // Move to the next level
            currentLevelIndex++;
        }
    }


}
