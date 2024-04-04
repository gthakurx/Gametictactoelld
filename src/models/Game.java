package models;

import strategy.winningStrategy.WinningStrategy;

import java.util.ArrayList;
import java.util.List;

//All attributes must be private
// Have getters and setters for each
//ensure all the attribute are initialized in constructor
public class Game {
    private List<Move> moves;
    private Board board;
    private List<Player> players;
    private Player winner ;
    private List<WinningStrategy> winningStrategies;
    private GameState gameState;
    private int nextMovePlayerIdx;
//Game constructor will be called by the user who are creating the game
    private Game(int dimension,List<Player> players,List<WinningStrategy> winningStrategies){
        this.moves=new ArrayList<>();
        this.players=players;
        this.board=new Board(dimension);
        this.winningStrategies=winningStrategies;
        this.gameState=GameState.IN_PROGRESS;
        this.nextMovePlayerIdx=0;

    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void setWinningStrategies(List<WinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getNextMovePlayerIdx() {
        return nextMovePlayerIdx;
    }

    public void setNextMovePlayerIdx(int nextMovePlayerIdx) {
        this.nextMovePlayerIdx = nextMovePlayerIdx;
    }
}
