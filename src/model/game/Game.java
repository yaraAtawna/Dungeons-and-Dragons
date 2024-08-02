package model.game;

import control.initializers.LevelInitializer;
import utils.callbacks.DeathCallback;
import utils.callbacks.MessageCallback;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Game
{
    private List<Path> levelPaths;
    private int currentLevelIndex;
    private boolean gameIsOver;
    private Board board;
    private List<File> filesOfLevels;
    private  int levelsNum;
    private int playerIndex;
    private String path;
    private LevelInitializer levelInitializer;
    Scanner scanner ;


    // update 15/7
    public Game(String path, MessageCallback messageCallback, DeathCallback deathCallback) {
        //this.levelPaths = levelPaths;
        this.currentLevelIndex = 0;
        this.gameIsOver = false;
        this.path=path;
        this.scanner = new Scanner(System.in);

    }

    // update 3/8
    public void start()
    {
        printPlayersMenu();
        userChoosePlayer();
        List<Path> levels=getAllFilesInDirectory(path);
        levelInitializer = new LevelInitializer(playerIndex, messageCallback,  deathCallback);

        while (currentLevelIndex < levels.size() && !gameIsOver) {
            // Load the next level
            loadLevel(levels.get(currentLevelIndex).toString());
            printBoard();
            Level level = new Level(board);

            // Start the level
            level.start();

            // Move to the next level
            currentLevelIndex++;
        }
    }

    private void printBoard()
    {
        // Print the board
    }

    private void loadLevel(String LevPath)
    {
        board = levelInitializer.initLevel(LevPath);
        this.levelsNum=levelInitializer.getLevelsNum();
    }

    private void userChoosePlayer()
    {
        // Get the user's choice
        int playerI = scanner.nextInt();
        // Set the playerIndex to the user's choice
        this.playerIndex = playerI;
    }

    private void printPlayersMenu()
    {
        // Print the players menu
    }
    public static List<Path> getAllFilesInDirectory(String directoryPath) {
        try (Stream<Path> paths = Files.list(Paths.get(directoryPath))) {
            return paths.filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Failed to list files in directory: " + directoryPath, e);
        }
    }




}
