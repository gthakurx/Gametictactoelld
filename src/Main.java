import controllers.GameController;
import models.*;
import strategy.winningStrategy.OrderOneColoumnWinningStrategy;
import strategy.winningStrategy.OrderOneDiagonalWinningStrategy;
import strategy.winningStrategy.OrderOneRowWinningStrategy;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //create a game
        GameController gameController=new GameController();
        Scanner scanner=new Scanner(System.in);
        Game game;
        try{
            game=gameController.createGame(3, List.of(
                    new Player(new Symbol('X'),"Gaurav", PlayerType.HUMAN),
                    new Bot(new Symbol('O'),"Aman",BotDifficultyLevel.EASY)
                    ),
                    List.of(
                            new OrderOneDiagonalWinningStrategy(),
                            new OrderOneColoumnWinningStrategy(),
                            new OrderOneRowWinningStrategy()
                    ));
        }catch(Exception e){
            System.out.println("Something went wrong ");
            return ;
        }
        //while game status in progress
        while(gameController.getGameStatus(game).equals(GameStatus.IN_PROGRESS)) {

            //print board
            gameController.displayBoard(game);
            //orint undo
            System.out.println("Do you want to undo (y/n)");
            //if Yes --> call undo
            String input=scanner.next();
            if(input.equalsIgnoreCase("y")){
                gameController.undo(game);
            }else{
                gameController.makeMove(game);
            }

            //move next player
        }
        //check status of Game
//        GameStatus gameStatus=gameController.getGameStatus();
//        if(gameStatus.equals(GameStatus.ENDED)){
//            gameController.printWinner();
//        }else{
//            System.out.println("Game is draw");
//        }
        gameController.printResult(game);
            //if winner print winner
            //else print draw
    }
}