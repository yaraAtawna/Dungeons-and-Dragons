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
//import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
import java.util.*;

public class Game
{
    private List<Path> levelPaths;
    private int currentLevelIndex;
    //private boolean gameIsOver;
    private Board board;
    private List<File> filesOfLevels;
    private  int levelsNum;
    private int playerIndex;
    private String path;
    private LevelInitializer levelInitializer;
    Scanner scanner ;
    private MessageCallback messageCallback;
    private DeathCallback deathCallback;


    //
    public Game(String path, MessageCallback messageCallback, DeathCallback deathCallback) {
        //this.levelPaths = levelPaths;
        this.currentLevelIndex = 0;
        //this.gameIsOver = false;
        this.path=path;
        this.scanner = new Scanner(System.in);
        this.messageCallback = messageCallback;
        this.deathCallback = deathCallback;

    }

    //
    public void start()
    {
        printPlayersMenu(); //show the users the players menu
        userChoosePlayer(); // the player choose his player
        List<Path> levels=getAllFilesInDirectory(path); //get all the levels in the directory
        levelInitializer = new LevelInitializer(playerIndex, messageCallback,  deathCallback);
        while (currentLevelIndex < levels.size() && gameIsNotOver()) {
            // Load the next level
            loadLevel(levels.get(currentLevelIndex).toString());
            //print the board
            printBoard();
            //initialize the level
            Level level = new Level(board, messageCallback, deathCallback);
            // Start the level
            level.start();
            // Move to the next level
            currentLevelIndex++;
        }
    }
    private boolean gameIsNotOver()
    {
        return board.getPlayer().alive();

    }
    private void printBoard()
    {
        // Print the board
        messageCallback.send(board.toString());
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
        messageCallback.send("Select player:");
        messageCallback.send("1. Jon Snow             Health: 300/300         Attack: 30              Defense: 4              Level: 1               Experience: 0/50         Cooldown: 0/3");
        messageCallback.send("2. The Hound            Health: 400/400         Attack: 20              Defense: 6              Level: 1               Experience: 0/50         Cooldown: 0/5");
        messageCallback.send("3. Melisandre           Health: 100/100         Attack: 5               Defense: 1              Level: 1               Experience: 0/50         Mana: 75/300            Spell Power: 15");
        messageCallback.send("4. Thoros of Myr        Health: 250/250         Attack: 25              Defense: 4              Level: 1               Experience: 0/50         Mana: 37/150            Spell Power: 20");
        messageCallback.send("5. Arya Stark           Health: 150/150         Attack: 40              Defense: 2              Level: 1               Experience: 0/50         Energy: 100/100");
        messageCallback.send("6. Bronn                Health: 250/250         Attack: 35              Defense: 3              Level: 1               Experience: 0/50         Energy: 100/100");
    }
    public static List<Path> getAllFilesInDirectory(String directoryPath) {
        List<Path> paths = new ArrayList<>();
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("The provided path is not a valid directory.");
        }
        // Regular expression to match files named "level<i>.txt"
        Pattern pattern = Pattern.compile("level\\d+\\.txt");
        // Get all files in the directory
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                // Check if the file matches the pattern
                Matcher matcher = pattern.matcher(file.getName());
                if (matcher.matches()) {
                    paths.add(file.toPath());
                }
            }
        }

        return paths;
    }

//        try (Stream<Path> paths = Files.list(Paths.get(directoryPath))) {
//            return paths.filter(Files::isRegularFile)
//                    .collect(Collectors.toList());
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to list files in directory: " + directoryPath, e);
//        }



}
