package strategy.winningStrategy;

import models.Board;
import models.Move;

public interface WinningStrategy {
    boolean checkWinner(Board board, Move move);
    public void handleUndo(Board board,Move move);
}
