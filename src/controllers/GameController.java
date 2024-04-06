package controllers;

import models.Game;
import models.GameStatus;
import models.Player;
import strategy.winningStrategy.WinningStrategy;

import java.util.List;

public class GameController {
    public Game createGame(int dimension, List<Player> players,
                           List<WinningStrategy> winningStrategies){
        return Game.getBuilder()
                        .setDimensions(dimension)
                        .setPlayers(players)
                        .setWinningStrategies(winningStrategies)
                        .build();
    }
    public void displayBoard(Game game){
        //should we write the buisness class here ? no
            game.printBoard();
    }
    public void undo(Game game){
        game.undo();
    }
    public void makeMove(Game game){
        game.makeMove();
    }
    public GameStatus getGameStatus(Game game){
        return game.getGameStatus();
    }
    public void printResult(Game game){
        game.printResult();
    }
}
