import controllers.GameController;
import exceptions.InvalidGameParamException;
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
        int dimension=3;
        List<Player> players=List.of(
                new Player(new Symbol('X'),"Gaurav", PlayerType.HUMAN),
                new Bot(new Symbol('O'),"Aman",BotDifficultyLevel.EASY)
        );
        try{
            game=gameController.createGame(dimension,
                    players,
                    List.of(
                            new OrderOneDiagonalWinningStrategy(players),
                            new OrderOneColoumnWinningStrategy(dimension,players),
                            new OrderOneRowWinningStrategy(dimension,players)
                    )
            );
        }catch(InvalidGameParamException e){
            System.out.println("Something went wrong ");
            return ;
        }
        //while game status in progress
        System.out.println("-------- Game is starting ---------");
        while(gameController.getGameStatus(game).equals(GameStatus.IN_PROGRESS)) {

            System.out.println("-------how Board looks like -----------");
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
        gameController.displayBoard(game);
            //if winner print winner
            //else print draw
    }
}