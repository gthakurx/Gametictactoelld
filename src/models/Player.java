package models;

import java.util.Scanner;

public class Player {
    private Symbol symbol;
    private String name;
    private PlayerType playerType;

    private Scanner scanner;

    public Player(Symbol symbol, String name , PlayerType playerType){
        this.symbol=symbol;
        this.name=name;
        this.playerType=PlayerType.HUMAN;
        this.scanner=new Scanner(System.in);
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }
    public Cell makeMove(Board board){
        System.out.println("Please tell Row no (starting from zero )");
        int row=scanner.nextInt();
        System.out.println("please tell Col no (starting from zero )");
        int col=scanner.nextInt();

        return new Cell(row,col);
    }
}
