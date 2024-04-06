package models;

import exceptions.InvalidGameParamException;
import strategy.winningStrategy.WinningStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//All attributes must be private
// Have getters and setters for each
//ensure all the attribute are initialized in constructor
public class Game {
    private List<Move> moves;
    private Board board;
    private List<Player> players;
    private Player winner ;
    private List<WinningStrategy> winningStrategies;
    private GameStatus gameStatus;
    private int currentMovePlayerIdx;
    private int nextMovePlayerIdx;
//Game constructor will be called by the user who are creating the game
    private Game(int dimension,List<Player> players,List<WinningStrategy> winningStrategies){
        this.moves=new ArrayList<>();
        this.players=players;
        this.board=new Board(dimension);
        this.winningStrategies=winningStrategies;
        this.gameStatus = GameStatus.IN_PROGRESS;
        this.nextMovePlayerIdx=0;
        this.currentMovePlayerIdx=0;

    }
    public static GameBuilder getBuilder(){
        return new GameBuilder();
    }

    public void printResult() {
        if(gameStatus.equals(GameStatus.ENDED)){
            System.out.println("Game has ended");
            System.out.println("Winner is "+winner.getName());
        }else{
            System.out.println("Game is draw");
        }
    }

    public static class GameBuilder{
        private List<Player> players;
        private List<WinningStrategy> winningStrategies;
        private int dimensions;

        private GameBuilder(){

        }

        public GameBuilder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public GameBuilder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        public GameBuilder setDimensions(int dimensions) {
            this.dimensions = dimensions;
            return this;
        }
        private boolean valid(){
            if(this.players.size()<2){
                return false;
            }
            if(this.players.size()!=this.dimensions-1){
                return false;
            }
            int botCount=0;
            for(Player player:this.players){
                if(player.getPlayerType().equals(PlayerType.BOT)){
                    botCount+=1;
                }
            }
            if(botCount >=2){
                return false;
            }
            Set<Character> existingSymbols= new HashSet<>();
            for(Player player: players){
                if(existingSymbols.contains(player.getSymbol().getAchar())){
                    return false;
                }
                existingSymbols.add(player.getSymbol().getAchar());
            }
            return true;
        }
        public Game build() throws InvalidGameParamException {
            if(!valid()){
                throw new InvalidGameParamException("Invalid params for the game");
            }
            return new Game(dimensions,players,winningStrategies);
        }
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

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getNextMovePlayerIdx() {
        return nextMovePlayerIdx;
    }

    public void setNextMovePlayerIdx(int nextMovePlayerIdx) {
        this.nextMovePlayerIdx = nextMovePlayerIdx;
    }

    public void printBoard(){
        board.print();
    }

    private boolean validateMove(Cell cell){
        int row=cell.getRow();
        int col=cell.getCol();
        if(row <0 || row >= board.getSize() || col <0 || col >=board.getSize() ){
            return false;
        }
        return board.getBoard().get(row).get(col).getCellState().equals(CellState.EMPTY);
    }
    public void undo(){
        if(moves.size()==0){
            System.out.println("No moves happend cannot undo");
            return ;
        }
        Move lastMove=moves.get(moves.size()-1);
        for(WinningStrategy winningStrategy:winningStrategies){
            winningStrategy.handleUndo(board,lastMove);
        }
        Cell cellInBoard=lastMove.getCell();
        cellInBoard.setCellState(CellState.EMPTY);
        cellInBoard.setPlayer(null);
        moves.remove(lastMove);

        currentMovePlayerIdx -=1;
        currentMovePlayerIdx +=players.size();
        currentMovePlayerIdx %=players.size();
    }
    // [A B C D]
    // ^
    // 0
    // -1
    // -1 % 4 == -1
    // (-1 + 4) == 3
    // 3 % 4 == 3
    public void makeMove(){
        Player currentPlayer=players.get(currentMovePlayerIdx);
        System.out.println("it is "+currentPlayer.getName()+"'s turn " +" CurrentMovePlayerIdx is :"+ currentMovePlayerIdx);
        Cell proposedCell=currentPlayer.makeMove(board);

        System.out.println("Moves made at row:"+proposedCell.getRow()+" col : "+ proposedCell.getCol());

        if(!validateMove(proposedCell)){
            System.out.println("invalid Move please try again ");
            return ;
        }
        Cell cellInBoard=board.getBoard().get(proposedCell.getRow()).get(proposedCell.getCol());
        cellInBoard.setCellState(CellState.FILLED);
        cellInBoard.setPlayer(currentPlayer);

        Move move=new Move(currentPlayer,cellInBoard);
        moves.add(move);
        //check winner
        if (checkGameWon(currentPlayer, move)) return;
        //check draw
        if(moves.size()== board.getSize()* board.getSize()){
            gameStatus=GameStatus.DRAW;
            return;
        }
        currentMovePlayerIdx+=1;
        currentMovePlayerIdx %=players.size(); // this will ensure the players are taking the turns
    }

    private boolean checkGameWon(Player currentPlayer, Move move) {
        for(WinningStrategy winningStrategy:winningStrategies){
            if(winningStrategy.checkWinner(board, move)){
                gameStatus=GameStatus.ENDED;
                winner= currentPlayer;
                return true;
            }
        }
        return false;
    }

}
